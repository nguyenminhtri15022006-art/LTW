# BT25-08-2026

Dự án web bán hàng / quản lý sản phẩm được xây dựng bằng Java, Spring Boot, JPA và JSP. Mục tiêu của ứng dụng là cung cấp một hệ thống cơ bản cho người dùng đăng ký, đăng nhập, quản lý danh mục sản phẩm, xem chi tiết sản phẩm, cập nhật hồ sơ cá nhân và upload hình ảnh.

## Tổng quan

Project này là một ứng dụng Java Web theo mô hình MVC kết hợp với Spring MVC, Hibernate/JPA và giao diện JSP/JSTL. Ứng dụng có tính năng giống một storefront đơn giản nhưng đã được tích hợp các chức năng xác thực người dùng, quản lý danh mục, quản lý sản phẩm và xử lý OTP để kích hoạt tài khoản hoặc reset mật khẩu.

## Tính năng chính

- Đăng ký tài khoản mới
- Kích hoạt tài khoản bằng mã OTP qua email
- Đăng nhập / đăng xuất
- Ghi nhớ username trên trình duyệt
- Quên mật khẩu và reset mật khẩu bằng OTP
- Quản lý danh mục sản phẩm
- Quản lý sản phẩm với CRUD
- Xem chi tiết sản phẩm
- Trang chủ hiển thị sản phẩm mới nhất
- Trang profile cá nhân
- Upload và xử lý ảnh đại diện / sản phẩm
- Validation dữ liệu ở server
- Layout chung cho các trang bằng SiteMesh
- Hỗ trợ kiểm thử bằng H2 trong môi trường test

## Công nghệ sử dụng

- Java 17
- Spring Boot 4.0.3
- Spring MVC
- Spring Data JPA / Hibernate
- Jakarta Validation
- JSP / JSTL
- Tomcat embedded + WAR deployment
- SQL Server (môi trường sản xuất)
- H2 Database (môi trường test)
- SiteMesh 3
- Jakarta Mail
- Maven

## Cấu trúc project

```text
BT25-08-2026/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/webapp/
│   │   ├── resources/
│   │   └── webapp/
│   └── test/
├── database/
├── pom.xml
├── README.md
├── PROJECT_CHANGES.md
└── IMPLEMENTATION_PLAN.md
```

## Mục tiêu của ứng dụng

Ứng dụng này được thiết kế để minh họa cách xây dựng một hệ thống web Java theo hướng thực tế, với:

- tầng controller xử lý request
- tầng service xử lý nghiệp vụ
- tầng repository / JPA xử lý dữ liệu
- giao diện JSP với layout chung
- bảo vệ route và xử lý session
- tối ưu cho học tập và làm bài tập môn Lập trình Web

## Quy trình chạy ứng dụng

### Yêu cầu

- JDK 17 trở lên
- Maven 3.9+
- SQL Server cho môi trường thực tế hoặc H2 cho test

### Chạy nhanh

```powershell
cd D:\UTE\Nam_3_2026_2027_1\nam3\LTW\BT25-08-2026
mvn spring-boot:run
```

Sau khi chạy, mở địa chỉ URL được in ra trong terminal hoặc truy cập theo context mặc định của ứng dụng.

### Build WAR

```powershell
mvn clean package
```

Khi build xong, file WAR sẽ nằm trong thư mục `target/`. Bạn có thể deploy lên Tomcat hoặc chạy bằng server tương thích.

## Cấu hình môi trường

Các thông tin cấu hình quan trọng nằm trong:

- `src/main/resources/application.properties`
- `src/main/resources/META-INF/persistence.xml`

Thông thường cần cấu hình các tham số như:

- URL kết nối database
- username/password database
- email SMTP
- thư mục lưu ảnh upload

## Hình ảnh và tài nguyên

Dự án hỗ trợ upload ảnh cho profile và sản phẩm. Ảnh được lưu trong thư mục cấu hình và có kiểm tra định dạng, kích thước và nội dung để tránh file không hợp lệ.

## Ghi chú

Dự án này phù hợp cho mục đích học tập, thực hành Java Web, và phát triển các tính năng cơ bản của hệ thống bán hàng. Nếu muốn mở rộng, có thể tiếp tục nâng cấp thêm:

- phân quyền admin/user rõ ràng hơn
- caching và search nâng cao
- API REST
- quản lý đơn hàng và giỏ hàng
- báo cáo thống kê

## Tài liệu liên quan

- [PROJECT_CHANGES.md](PROJECT_CHANGES.md)
- [IMPLEMENTATION_PLAN.md](IMPLEMENTATION_PLAN.md)
- [database/create-database.sql](database/create-database.sql)

## Mục đích

README này được viết theo hướng giới thiệu project, giúp người xem nhanh chóng hiểu dự án đang làm gì, công nghệ nào đang dùng, và cách bắt đầu sử dụng thay vì chỉ là một hướng dẫn build thuần túy.
