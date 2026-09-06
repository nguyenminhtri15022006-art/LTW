# Danh sách thay đổi project

Giữ project, package com.example.webapp, kiến trúc Servlet/Service/DAO/JPA và chức năng Category/Login gốc.

## File tạo mới (48)

- [.env.example](.env.example)
- [PROJECT_CHANGES.md](PROJECT_CHANGES.md)
- [README.md](README.md)
- [src/main/java/com/example/webapp/config/ApplicationFilter.java](src/main/java/com/example/webapp/config/ApplicationFilter.java)
- [src/main/java/com/example/webapp/config/ApplicationListener.java](src/main/java/com/example/webapp/config/ApplicationListener.java)
- [src/main/java/com/example/webapp/config/Environment.java](src/main/java/com/example/webapp/config/Environment.java)
- [src/main/java/com/example/webapp/config/LayoutFilter.java](src/main/java/com/example/webapp/config/LayoutFilter.java)
- [src/main/java/com/example/webapp/controller/AccountServlet.java](src/main/java/com/example/webapp/controller/AccountServlet.java)
- [src/main/java/com/example/webapp/controller/ImageServlet.java](src/main/java/com/example/webapp/controller/ImageServlet.java)
- [src/main/java/com/example/webapp/controller/ProductServlet.java](src/main/java/com/example/webapp/controller/ProductServlet.java)
- [src/main/java/com/example/webapp/controller/ProfileServlet.java](src/main/java/com/example/webapp/controller/ProfileServlet.java)
- [src/main/java/com/example/webapp/controller/WebSupport.java](src/main/java/com/example/webapp/controller/WebSupport.java)
- [src/main/java/com/example/webapp/dao/ProductDao.java](src/main/java/com/example/webapp/dao/ProductDao.java)
- [src/main/java/com/example/webapp/dao/ProductDaoImpl.java](src/main/java/com/example/webapp/dao/ProductDaoImpl.java)
- [src/main/java/com/example/webapp/dto/EmailDTO.java](src/main/java/com/example/webapp/dto/EmailDTO.java)
- [src/main/java/com/example/webapp/dto/OtpDTO.java](src/main/java/com/example/webapp/dto/OtpDTO.java)
- [src/main/java/com/example/webapp/dto/ProductDTO.java](src/main/java/com/example/webapp/dto/ProductDTO.java)
- [src/main/java/com/example/webapp/dto/ProfileDTO.java](src/main/java/com/example/webapp/dto/ProfileDTO.java)
- [src/main/java/com/example/webapp/dto/RegisterDTO.java](src/main/java/com/example/webapp/dto/RegisterDTO.java)
- [src/main/java/com/example/webapp/dto/ResetPasswordDTO.java](src/main/java/com/example/webapp/dto/ResetPasswordDTO.java)
- [src/main/java/com/example/webapp/entity/Product.java](src/main/java/com/example/webapp/entity/Product.java)
- [src/main/java/com/example/webapp/service/FormValidation.java](src/main/java/com/example/webapp/service/FormValidation.java)
- [src/main/java/com/example/webapp/service/MailService.java](src/main/java/com/example/webapp/service/MailService.java)
- [src/main/java/com/example/webapp/service/PasswordService.java](src/main/java/com/example/webapp/service/PasswordService.java)
- [src/main/java/com/example/webapp/service/ProductService.java](src/main/java/com/example/webapp/service/ProductService.java)
- [src/main/java/com/example/webapp/service/ProductServiceImpl.java](src/main/java/com/example/webapp/service/ProductServiceImpl.java)
- [src/main/java/com/example/webapp/service/UploadService.java](src/main/java/com/example/webapp/service/UploadService.java)
- [src/main/java/com/example/webapp/service/ValidationException.java](src/main/java/com/example/webapp/service/ValidationException.java)
- [src/main/resources/sql/product-schema-reference.sql](src/main/resources/sql/product-schema-reference.sql)
- [src/main/webapp/views/category/delete.jsp](src/main/webapp/views/category/delete.jsp)
- [src/main/webapp/views/forgot-password.jsp](src/main/webapp/views/forgot-password.jsp)
- [src/main/webapp/views/product/detail.jsp](src/main/webapp/views/product/detail.jsp)
- [src/main/webapp/views/product/form.jsp](src/main/webapp/views/product/form.jsp)
- [src/main/webapp/views/product/list.jsp](src/main/webapp/views/product/list.jsp)
- [src/main/webapp/views/profile.jsp](src/main/webapp/views/profile.jsp)
- [src/main/webapp/views/register.jsp](src/main/webapp/views/register.jsp)
- [src/main/webapp/views/reset-password.jsp](src/main/webapp/views/reset-password.jsp)
- [src/main/webapp/views/verify-otp.jsp](src/main/webapp/views/verify-otp.jsp)
- [src/main/webapp/WEB-INF/decorators/main.jsp](src/main/webapp/WEB-INF/decorators/main.jsp)
- [src/main/webapp/WEB-INF/fragments/messages.jspf](src/main/webapp/WEB-INF/fragments/messages.jspf)
- [src/main/webapp/WEB-INF/fragments/products.jspf](src/main/webapp/WEB-INF/fragments/products.jspf)
- [src/main/webapp/WEB-INF/fragments/upload.jspf](src/main/webapp/WEB-INF/fragments/upload.jspf)
- [src/main/webapp/WEB-INF/tags/input.tag](src/main/webapp/WEB-INF/tags/input.tag)
- [src/test/java/com/example/webapp/JspCompilationTest.java](src/test/java/com/example/webapp/JspCompilationTest.java)
- [src/test/java/com/example/webapp/ProductJpaTest.java](src/test/java/com/example/webapp/ProductJpaTest.java)
- [src/test/java/com/example/webapp/UploadServiceTest.java](src/test/java/com/example/webapp/UploadServiceTest.java)
- [src/test/java/com/example/webapp/UserServiceTest.java](src/test/java/com/example/webapp/UserServiceTest.java)
- [src/test/java/com/example/webapp/WebRuntimeTest.java](src/test/java/com/example/webapp/WebRuntimeTest.java)

## File đã sửa (23)

- [.gitignore](.gitignore)
- [pom.xml](pom.xml)
- [src/main/java/com/example/webapp/config/JpaConfig.java](src/main/java/com/example/webapp/config/JpaConfig.java)
- [src/main/java/com/example/webapp/controller/CategoryServlet.java](src/main/java/com/example/webapp/controller/CategoryServlet.java)
- [src/main/java/com/example/webapp/controller/HomeServlet.java](src/main/java/com/example/webapp/controller/HomeServlet.java)
- [src/main/java/com/example/webapp/controller/LoginServlet.java](src/main/java/com/example/webapp/controller/LoginServlet.java)
- [src/main/java/com/example/webapp/dao/UserDao.java](src/main/java/com/example/webapp/dao/UserDao.java)
- [src/main/java/com/example/webapp/dao/UserDaoImpl.java](src/main/java/com/example/webapp/dao/UserDaoImpl.java)
- [src/main/java/com/example/webapp/dto/CategoryDTO.java](src/main/java/com/example/webapp/dto/CategoryDTO.java)
- [src/main/java/com/example/webapp/dto/UserDTO.java](src/main/java/com/example/webapp/dto/UserDTO.java)
- [src/main/java/com/example/webapp/entity/User.java](src/main/java/com/example/webapp/entity/User.java)
- [src/main/java/com/example/webapp/service/CategoryServiceImpl.java](src/main/java/com/example/webapp/service/CategoryServiceImpl.java)
- [src/main/java/com/example/webapp/service/UserService.java](src/main/java/com/example/webapp/service/UserService.java)
- [src/main/java/com/example/webapp/service/UserServiceImpl.java](src/main/java/com/example/webapp/service/UserServiceImpl.java)
- [src/main/resources/META-INF/persistence.xml](src/main/resources/META-INF/persistence.xml)
- [src/main/webapp/WEB-INF/web.xml](src/main/webapp/WEB-INF/web.xml)
- [src/main/webapp/css/style.css](src/main/webapp/css/style.css)
- [src/main/webapp/views/category/add.jsp](src/main/webapp/views/category/add.jsp)
- [src/main/webapp/views/category/edit.jsp](src/main/webapp/views/category/edit.jsp)
- [src/main/webapp/views/category/list.jsp](src/main/webapp/views/category/list.jsp)
- [src/main/webapp/views/error.jsp](src/main/webapp/views/error.jsp)
- [src/main/webapp/views/index.jsp](src/main/webapp/views/index.jsp)
- [src/main/webapp/views/login.jsp](src/main/webapp/views/login.jsp)

## Cấu trúc project sau khi hoàn thành

Không liệt kê .git và target (output build/runtime test).

```text
BT25-08-2026/
  .env.example
  .gitignore
  pom.xml
  PROJECT_CHANGES.md
  README.md
  src/
    main/
      java/
        com/
          example/
            webapp/
              config/
                ApplicationFilter.java
                ApplicationListener.java
                Environment.java
                JpaConfig.java
                LayoutFilter.java
              controller/
                AccountServlet.java
                CategoryServlet.java
                ErrorServlet.java
                HomeServlet.java
                ImageServlet.java
                LoginServlet.java
                ProductServlet.java
                ProfileServlet.java
                WebSupport.java
              dao/
                CategoryDao.java
                CategoryDaoImpl.java
                ProductDao.java
                ProductDaoImpl.java
                UserDao.java
                UserDaoImpl.java
              dto/
                CategoryDTO.java
                EmailDTO.java
                LoginDTO.java
                OtpDTO.java
                ProductDTO.java
                ProfileDTO.java
                RegisterDTO.java
                ResetPasswordDTO.java
                UserDTO.java
              entity/
                Category.java
                Product.java
                User.java
              service/
                CategoryService.java
                CategoryServiceImpl.java
                FormValidation.java
                MailService.java
                PasswordService.java
                ProductService.java
                ProductServiceImpl.java
                UploadService.java
                UserService.java
                UserServiceImpl.java
                ValidationException.java
      resources/
        META-INF/
          persistence.xml
        sql/
          product-schema-reference.sql
          test-users.sql
      webapp/
        css/
          style.css
        index.jsp
        views/
          category/
            add.jsp
            delete.jsp
            edit.jsp
            list.jsp
          error.jsp
          forgot-password.jsp
          index.jsp
          login.jsp
          product/
            detail.jsp
            form.jsp
            list.jsp
          profile.jsp
          register.jsp
          reset-password.jsp
          verify-otp.jsp
        WEB-INF/
          decorators/
            main.jsp
          fragments/
            messages.jspf
            products.jspf
            upload.jspf
          tags/
            input.tag
          web.xml
    test/
      java/
        com/
          example/
            webapp/
              JspCompilationTest.java
              ProductJpaTest.java
              UploadServiceTest.java
              UserServiceTest.java
              WebRuntimeTest.java
```

URL, biến môi trường, SQL Server, Gmail App Password, lệnh build/deploy và checklist kiểm thử: [README.md](README.md).
