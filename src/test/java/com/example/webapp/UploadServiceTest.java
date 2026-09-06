package com.example.webapp;

import static org.junit.jupiter.api.Assertions.*;

import com.example.webapp.service.*;

import jakarta.servlet.http.Part;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;
import java.nio.file.*;
import java.util.*;

class UploadServiceTest {
    @TempDir Path temp;
    UploadService service;

    @BeforeEach
    void setup() {
        service =
                new UploadService() {
                    public Path directory() {
                        return temp;
                    }
                };
    }

    @Test
    void validImageUsesGeneratedNameAndCanBeRead() throws Exception {
        byte[] png =
                Base64.getDecoder()
                        .decode(
                                "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mP8/x8AAwMCAO+jRZkAAAAASUVORK5CYII=");
        String name = service.save(new TestPart("../../original.png", png, png.length));
        assertFalse(name.contains("original"));
        assertArrayEquals(png, Files.readAllBytes(service.resolve(name)));
        service.discard(name);
        assertFalse(Files.exists(temp.resolve(name)));
    }

    @Test
    void rejectsFakeImagesAndOversizeAndEmptyFiles() {
        byte[] truncated = new byte[20];
        truncated[0] = (byte) 255;
        truncated[1] = (byte) 216;
        truncated[2] = (byte) 255;
        assertThrows(
                ValidationException.class,
                () -> service.save(new TestPart("truncated.jpg", truncated, 20)));
        assertThrows(
                ValidationException.class,
                () -> service.save(new TestPart("fake.jpg", "<html>fake</html>".getBytes(), 17)));
        assertThrows(
                ValidationException.class,
                () ->
                        service.save(
                                new TestPart("huge.png", new byte[0], UploadService.MAX_SIZE + 1)));
        assertThrows(
                ValidationException.class,
                () -> service.save(new TestPart("empty.png", new byte[0], 0)));
        assertThrows(
                ValidationException.class,
                () -> service.save(new TestPart("evil.jsp", new byte[20], 20)));
    }

    @Test
    void absentFileIsOptionalAndTraversalCannotBeServed() throws Exception {
        assertNull(service.save(null));
        assertNull(service.save(new TestPart("", new byte[0], 0)));
        assertNull(service.resolve("../pom.xml"));
        assertNull(service.resolve("C:/secret.png"));
    }

    record TestPart(String filename, byte[] bytes, long size) implements Part {
        public InputStream getInputStream() {
            return new ByteArrayInputStream(bytes);
        }

        public String getContentType() {
            return "image/png";
        }

        public String getName() {
            return "image";
        }

        public String getSubmittedFileName() {
            return filename;
        }

        public long getSize() {
            return size;
        }

        public void write(String fileName) {}

        public void delete() {}

        public String getHeader(String name) {
            return null;
        }

        public Collection<String> getHeaders(String name) {
            return List.of();
        }

        public Collection<String> getHeaderNames() {
            return List.of();
        }
    }
}
