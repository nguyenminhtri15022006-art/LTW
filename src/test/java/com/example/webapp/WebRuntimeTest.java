package com.example.webapp;

import static org.junit.jupiter.api.Assertions.*;

import com.example.webapp.dto.*;
import com.example.webapp.service.UploadService;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.*;
import org.junit.jupiter.api.*;

import java.io.*;
import java.net.*;
import java.net.http.*;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;

class WebRuntimeTest {
    static Tomcat tomcat;
    static String base;
    static HttpClient client;

    @BeforeAll
    static void start() throws Exception {
        tomcat = new Tomcat();
        tomcat.setBaseDir(Path.of("target", "tomcat-test").toAbsolutePath().toString());
        tomcat.setPort(0);
        tomcat.getConnector().setProperty("address", "127.0.0.1");
        var context =
                tomcat.addWebapp(
                        "/test", Path.of("src", "main", "webapp").toAbsolutePath().toString());
        context.setParentClassLoader(WebRuntimeTest.class.getClassLoader());
        var resources = new StandardRoot(context);
        resources.addPreResources(
                new DirResourceSet(
                        resources,
                        "/WEB-INF/classes",
                        Path.of("target", "classes").toAbsolutePath().toString(),
                        "/"));
        context.setResources(resources);
        var fixture =
                Tomcat.addServlet(
                        context,
                        "fixture",
                        new HttpServlet() {
                            protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                                    throws ServletException, IOException {
                                String view = req.getParameter("view");
                                if (view == null
                                        || !Set.of(
                                                        "index",
                                                        "profile",
                                                        "product/list",
                                                        "product/detail",
                                                        "product/form",
                                                        "category/list",
                                                        "category/add",
                                                        "category/edit",
                                                        "category/delete")
                                                .contains(view)) {
                                    resp.sendError(400);
                                    return;
                                }
                                UserDTO user =
                                        new UserDTO(1L, "test", "Tên <script>alert(1)</script>");
                                req.getSession().setAttribute("currentUser", user);
                                ProductDTO p = new ProductDTO();
                                p.setId(1L);
                                p.setName("Product <script>alert(1)</script>");
                                p.setCategoryId(1);
                                p.setCategoryName("Category");
                                p.setPrice(java.math.BigDecimal.TEN);
                                p.setStock(5);
                                req.setAttribute("product", p);
                                req.setAttribute("products", List.of(p));
                                req.setAttribute(
                                        "categories", List.of(new CategoryDTO(1, "Category")));
                                req.setAttribute("category", new CategoryDTO(1, "Category"));
                                req.setAttribute("currentPage", 1);
                                req.setAttribute("totalPages", 3);
                                req.setAttribute("pageSize", 6);
                                req.setAttribute("totalProducts", 13L);
                                req.getRequestDispatcher("/views/" + view + ".jsp")
                                        .forward(req, resp);
                            }

                            protected void doPost(HttpServletRequest req, HttpServletResponse resp)
                                    throws ServletException, IOException {
                                Part part = req.getPart("image");
                                resp.setContentType("text/plain");
                                resp.getWriter()
                                        .write(req.getParameter("fullName") + ":" + part.getSize());
                            }
                        });
        fixture.setMultipartConfigElement(
                new MultipartConfigElement("", UploadService.MAX_SIZE, 6291456, 0));
        context.addServletMappingDecoded("/fixture", "fixture");
        tomcat.start();
        assertTrue(
                context.getState().isAvailable(),
                "Tomcat webapp must start without duplicate mappings");
        base = "http://127.0.0.1:" + tomcat.getConnector().getLocalPort() + "/test";
        client =
                HttpClient.newBuilder()
                        .cookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_ALL))
                        .build();
    }

    @AfterAll
    static void stop() throws Exception {
        if (tomcat != null) {
            tomcat.stop();
            tomcat.destroy();
        }
    }

    static HttpResponse<String> get(String path) throws Exception {
        return client.send(
                HttpRequest.newBuilder(URI.create(base + path)).GET().build(),
                HttpResponse.BodyHandlers.ofString());
    }

    static String csrf(String html) {
        Matcher m = Pattern.compile("name=\"csrf\" value=\"([^\"]+)\"").matcher(html);
        assertTrue(m.find());
        return m.group(1);
    }

    @Test
    void publicAccountPagesHaveOneDecorator() throws Exception {
        for (String route :
                List.of(
                        "/login",
                        "/home/login",
                        "/register",
                        "/verify-otp",
                        "/forgot-password",
                        "/reset-password")) {
            var r = get(route);
            assertEquals(200, r.statusCode(), r.body());
            assertTrue(r.body().contains("navbar-brand"), route);
            assertEquals(1, r.body().split("<main", -1).length - 1);
            assertFalse(r.body().contains("<sitemesh:write"));
            csrf(r.body());
        }
    }

    @Test
    void allMainViewsRenderAndEscapeUserText() throws Exception {
        for (String view :
                List.of(
                        "index",
                        "profile",
                        "product/list",
                        "product/detail",
                        "product/form",
                        "category/list",
                        "category/add",
                        "category/edit",
                        "category/delete")) {
            var r = get("/fixture?view=" + view);
            assertEquals(200, r.statusCode(), r.body());
            assertTrue(r.body().contains("navbar-brand"), view);
            assertFalse(r.body().contains("<script>alert(1)</script>"), view);
            assertTrue(r.body().contains("&lt;script&gt;"), view);
        }
    }

    @Test
    void loginValidationAndCsrfWork() throws Exception {
        String token = csrf(get("/login").body());
        var req =
                HttpRequest.newBuilder(URI.create(base + "/login"))
                        .header("Content-Type", "application/x-www-form-urlencoded")
                        .POST(
                                HttpRequest.BodyPublishers.ofString(
                                        "csrf=" + token + "&username=&password="))
                        .build();
        var r = client.send(req, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, r.statusCode());
        assertTrue(r.body().contains("Nhập username."));
        var bad =
                HttpRequest.newBuilder(URI.create(base + "/login"))
                        .header("Content-Type", "application/x-www-form-urlencoded")
                        .POST(HttpRequest.BodyPublishers.ofString("username=x"))
                        .build();
        assertEquals(403, client.send(bad, HttpResponse.BodyHandlers.ofString()).statusCode());
    }

    @Test
    void multipartIsAvailableAfterCsrfFilter() throws Exception {
        String token = csrf(get("/login").body()), boundary = "WebJavaBoundary";
        String body =
                "--"
                        + boundary
                        + "\r\nContent-Disposition: form-data; name=\"csrf\"\r\n\r\n"
                        + token
                        + "\r\n--"
                        + boundary
                        + "\r\n"
                        + "Content-Disposition: form-data; name=\"fullName\"\r\n\r\n"
                        + "Nguyễn Văn A\r\n"
                        + "--"
                        + boundary
                        + "\r\n"
                        + "Content-Disposition: form-data; name=\"image\";"
                        + " filename=\"test.png\"\r\n"
                        + "Content-Type: image/png\r\n\r\n"
                        + "abc\r\n"
                        + "--"
                        + boundary
                        + "--\r\n";
        var req =
                HttpRequest.newBuilder(URI.create(base + "/fixture"))
                        .header("Content-Type", "multipart/form-data; boundary=" + boundary)
                        .POST(HttpRequest.BodyPublishers.ofString(body))
                        .build();
        var r = client.send(req, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, r.statusCode(), r.body());
        assertEquals("Nguyễn Văn A:3", r.body());
    }

    @Test
    void profileRequiresLoginAndRawViewsAreHidden() throws Exception {
        HttpClient anonymous = HttpClient.newHttpClient();
        var r =
                anonymous.send(
                        HttpRequest.newBuilder(URI.create(base + "/profile")).GET().build(),
                        HttpResponse.BodyHandlers.ofString());
        assertEquals(302, r.statusCode());
        assertTrue(r.headers().firstValue("Location").orElse("").endsWith("/login"));
        assertEquals(404, get("/views/profile.jsp").statusCode());
        assertEquals(404, get("/images/not-an-image").statusCode());
    }
}
