<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>

        <!DOCTYPE html>
        <html>

        <head>
            <meta charset="UTF-8">
            <title>Category Management</title>

            <style>
                body {
                    font-family: Arial, sans-serif;
                    margin: 40px;
                }

                h1 {
                    margin-bottom: 20px;
                }

                table {
                    width: 700px;
                    border-collapse: collapse;
                    margin-top: 20px;
                }

                th,
                td {
                    border: 1px solid #ccc;
                    padding: 10px;
                    text-align: left;
                }

                th {
                    background-color: #f2f2f2;
                }

                a {
                    text-decoration: none;
                    margin-right: 10px;
                }

                .add-btn {
                    display: inline-block;
                    padding: 8px 14px;
                    border: 1px solid #333;
                    margin-bottom: 10px;
                }

                .back-btn {
                    display: inline-block;
                    margin-top: 20px;
                }
            </style>
        </head>

        <body>

            <h1>Category Management</h1>

            <a class="add-btn" href="${pageContext.request.contextPath}/category?action=add">
                Add Category
            </a>

            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Category Name</th>
                        <th>Actions</th>
                    </tr>
                </thead>

                <tbody>

                    <c:forEach var="category" items="${categories}">
                        <tr>

                            <td>
                                <c:out value="${category.id}" />
                            </td>

                            <td>
                                <c:out value="${category.name}" />
                            </td>

                            <td>

                                <a href="${pageContext.request.contextPath}/category?action=edit&id=${category.id}">
                                    Edit
                                </a>

                                <a href="${pageContext.request.contextPath}/category?action=delete&id=${category.id}"
                                    onclick="return confirm('Are you sure you want to delete this category?');">
                                    Delete
                                </a>

                            </td>

                        </tr>
                    </c:forEach>

                </tbody>
            </table>

            <c:if test="${empty categories}">
                <p>No categories found.</p>
            </c:if>

            <a class="back-btn" href="${pageContext.request.contextPath}/home">
                Back to Home
            </a>

        </body>

        </html>