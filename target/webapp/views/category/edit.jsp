<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>

        <!DOCTYPE html>
        <html>

        <head>
            <meta charset="UTF-8">
            <title>Edit Category</title>

            <style>
                body {
                    font-family: Arial, sans-serif;
                    margin: 40px;
                }

                .form-group {
                    margin-bottom: 15px;
                }

                input[type="text"] {
                    width: 300px;
                    padding: 8px;
                }

                button {
                    padding: 8px 15px;
                    cursor: pointer;
                }

                a {
                    margin-left: 10px;
                    text-decoration: none;
                }
            </style>
        </head>

        <body>

            <h1>Edit Category</h1>

            <form method="post" action="${pageContext.request.contextPath}/category?action=update">

                <input type="hidden" name="id" value="${category.id}">

                <div class="form-group">

                    <label>
                        ID:
                    </label>

                    <strong>
                        <c:out value="${category.id}" />
                    </strong>

                </div>

                <div class="form-group">

                    <label for="name">
                        Category Name:
                    </label>

                    <br>

                    <input type="text" id="name" name="name" value="<c:out value='${category.name}'/>" required>

                </div>

                <button type="submit">
                    Update
                </button>

                <a href="${pageContext.request.contextPath}/category?action=list">
                    Cancel
                </a>

            </form>

        </body>

        </html>