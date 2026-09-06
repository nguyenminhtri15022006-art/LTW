# BT25-08-2026 — Java Web

Tiếp tục từ project gốc, giữ package `com.example.webapp` và kiến trúc:

`JSP/JSTL → Servlet → Service → DAO → JPA/Hibernate → SQL Server`

Chức năng: Category CRUD, đăng nhập/logout/remember username, Register + OTP email, Forgot/Reset Password + OTP, Product CRUD/detail, Home 10 sản phẩm mới nhất, danh sách 6 sản phẩm/trang, Profile và upload ảnh. Các trang dùng chung Bootstrap qua SiteMesh 3.

## Build và chạy

Yêu cầu JDK 17 trở lên, Maven 3.9, SQL Server và Tomcat 10.1. Project biên dịch với Java release 17; đã bỏ cấu hình Java 25 không khớp trước đây.

```powershell
mvn clean package
```

Kết quả: `target/BT25-08-2026.war`. Test được chạy trong lệnh trên, gồm JUnit, JPA với H2 tạm, Jasper biên dịch JSP và Tomcat localhost tạm. Test không gửi email thật và không kết nối database SQL Server của bạn.

Kết quả kiểm tra ngày 06/09/2026: `mvn clean package` BUILD SUCCESS; 21 test, 0 lỗi, 0 thất bại. Toàn bộ JSP/tag biên dịch thành công; test HTTP xác nhận SiteMesh, mapping, CSRF, multipart và bảo vệ Profile. SQL Server và SMTP thật chưa được kiểm thử vì chưa có biến môi trường DB/mail trong phiên làm việc.

Deploy WAR vào `webapps` của Tomcat 10.1 đã cấu hình biến môi trường, sau đó khởi động Tomcat. Ví dụ khi đã đặt `CATALINA_HOME`:

```powershell
Copy-Item target/BT25-08-2026.war "$env:CATALINA_HOME/webapps/"
& "$env:CATALINA_HOME/bin/catalina.bat" run
```

Linux/macOS: copy WAR vào `$CATALINA_HOME/webapps/`, chạy `$CATALINA_HOME/bin/catalina.sh run`.
Nếu chạy bằng IDE hoặc Windows Service, khai báo biến môi trường cho chính tiến trình Tomcat và restart tiến trình.

Địa chỉ mặc định: `http://localhost:8080/BT25-08-2026`. Đổi port/context nếu cấu hình server của bạn khác.

## Biến môi trường

| Biến | Bắt buộc | Giá trị / ý nghĩa |
|---|---|---|
| DB_URL | Có | JDBC URL SQL Server, trỏ database hiện tại |
| DB_USERNAME | Có | SQL login có quyền truy cập database |
| DB_PASSWORD | Có | Mật khẩu SQL login |
| MAIL_USERNAME | Gửi OTP | Email tài khoản SMTP |
| MAIL_PASSWORD | Gửi OTP | SMTP password / Gmail App Password |
| MAIL_HOST | Không | Mặc định smtp.gmail.com |
| MAIL_PORT | Không | Mặc định 587 (STARTTLS); 465 dùng SSL |
| MAIL_FROM | Không | Mặc định MAIL_USERNAME; nếu đổi phải được SMTP cho phép |
| UPLOAD_DIR | Nên đặt | Thư mục dữ liệu ảnh bên ngoài WAR, tiến trình Tomcat có quyền ghi |

Ví dụ DB_URL cho SQL Server local:
`jdbc:sqlserver://localhost:1433;databaseName=jakartaJPA;encrypt=true;trustServerCertificate=true`.
Thay hostname, port và database đúng máy bạn; tùy chọn trustServerCertificate phù hợp server thực hành dùng chứng chỉ tự ký.

`.env.example` chỉ là danh sách biến mẫu. Ứng dụng đọc `System.getenv()`, không tự nạp file `.env`.
Không ghi mật khẩu thật vào source hoặc commit file môi trường.

Nếu không đặt UPLOAD_DIR, ảnh nằm trong thư mục `BT25-08-2026-uploads` dưới `java.io.tmpdir`. Đặt UPLOAD_DIR bền vững để tránh mất ảnh khi dọn thư mục tạm. Database lưu tên UUID; URL ảnh là `/images/{filename}`.

## SQL Server và dữ liệu cũ

1. Tạo database nếu chưa có, hoặc dùng đúng database đang chứa `users` và `categories`.
2. Bật TCP/IP, cấu hình port và SQL authentication phù hợp.
3. Cấp quyền SELECT/INSERT/UPDATE/DELETE và quyền DDL cần cho Hibernate update/index trong database bài tập.
4. Giữ `hibernate.hbm2ddl.auto=update` trong persistence.xml. Hibernate thêm các cột User và bảng Product; không drop dữ liệu cũ.
5. JpaConfig khởi tạo filtered unique index `UX_users_email` (`WHERE email IS NOT NULL`) vì SQL Server cần cho phép nhiều tài khoản cũ chưa có email. Tài khoản đăng ký mới bắt buộc có email, được kiểm tra trùng tại service và database.
6. Khi nâng cấp, JpaConfig điền version=0 và active=1 cho các bản ghi cũ có giá trị NULL; đăng ký mới luôn gán active=false. Mật khẩu plaintext của tài khoản cũ được chuyển sang PBKDF2 khi đăng nhập thành công. Mật khẩu đăng ký/reset luôn được băm ngay.
7. `src/main/resources/sql/test-users.sql` là script mẫu cũ, giữ nguyên và không tự chạy. Chỉ chạy thủ công khi cần trên database thực hành; chạy lặp sẽ trùng username. Không cần chạy script này để sử dụng Register.
8. `src/main/resources/sql/product-schema-reference.sql` là DDL tham khảo; source vận hành vẫn dùng JPA.

Product dùng `@ManyToOne Category`, không có một field category_id độc lập trong Entity. Category không cascade delete Product. Phải chuyển/xóa Product trước khi xóa Category đang được tham chiếu.

## Gmail App Password

Bật xác minh 2 bước, tạo App Password cho tài khoản được phép dùng chức năng này, rồi đặt vào MAIL_PASSWORD. Không dùng mật khẩu đăng nhập Gmail thông thường trong source.

Tài khoản tổ chức hoặc có chính sách bảo vệ đặc biệt có thể không cung cấp App Password. Xem [hướng dẫn chính thức của Google](https://support.google.com/accounts/answer/185833).

Nếu SMTP lỗi, giao diện báo lỗi email; tài khoản vẫn chưa kích hoạt và có thể gửi lại ở /verify-otp. Không cần đăng ký lại username/email đã tạo.

## URL kiểm thử

Các đường dẫn dưới đây nối sau context `/BT25-08-2026`:

| URL | Chức năng |
|---|---|
| / hoặc /home | Tối đa 10 Product mới nhất, createdAt DESC rồi id DESC |
| /register | Đăng ký username/password/fullName/email |
| /verify-otp | Nhập email + OTP kích hoạt, hoặc gửi lại OTP |
| /login | Login, remember username |
| /home/login | Alias login cũ |
| /logout | Logout; nút trên navbar gửi POST và invalidate session |
| /login?action=logout | Giữ alias logout cũ |
| /forgot-password | Email → gửi OTP reset |
| /reset-password | Email + OTP + mật khẩu mới |
| /profile | Xem/sửa họ tên, phone, upload ảnh; yêu cầu đăng nhập |
| /category | Danh sách Category |
| /category?action=add | Form thêm Category |
| /category?action=edit&id=1 | Form sửa Category |
| /category?action=delete&id=1 | Trang xác nhận xóa, thao tác thật dùng POST |
| /product?page=1 | Product trang 1, pageSize=6 |
| /product?page=2 | Product trang 2 |
| /product?action=add | Form tạo Product, multipart |
| /product?action=edit&id=1 | Form sửa Product, multipart |
| /product?action=detail&id=1 | Detail; có nút sửa/xóa sau login |
| /images/{filename} | Phục vụ ảnh đã upload |
| /error | Trang lỗi |

ID trong ví dụ phải thay bằng ID có thật. Product xóa bằng POST action=delete qua nút ở detail.
Category/Product tạo/sửa/xóa cần đăng nhập; đọc danh sách/detail được mở công khai.
Project gốc chưa có role quản trị nên chưa thêm mô hình phân quyền mới.
JSP /views không truy cập trực tiếp; phải qua servlet. Session chỉ chứa UserDTO và dữ liệu form tạm, không chứa User Entity/password/OTP.

## Validation và OTP

- Server dùng Jakarta Validation ở DTO/service; lỗi hiển thị gần field, dữ liệu phù hợp được giữ lại, mật khẩu/file phải nhập/chọn lại.
- OTP sinh bằng SecureRandom, đúng 6 chữ số, có hạn 5 phút, lưu digest SHA-256.
- Tối đa 5 lần nhập sai cho mỗi OTP; gửi lại cách nhau ít nhất 60 giây. Gửi lại thay mã cũ.
- OTP kích hoạt và OTP reset là hai luồng độc lập. Thành công xóa mã/thời hạn; reset không tự kích hoạt tài khoản.
- DAO khóa hàng khi thay OTP/password/profile để tránh dùng một mã đồng thời nhiều lần.
- POST form có CSRF token; phiên form hết hạn cần tải lại trang.
- Login đổi session ID; remember cookie chỉ lưu username, HttpOnly, SameSite=Lax, Secure khi HTTPS.
- Upload tối đa 5 MB/file, 6 MB/request, kiểm tra đuôi và nội dung ảnh, UUID filename, không dùng đường dẫn client. JPG/PNG/GIF được đọc kiểm tra (tối đa 25 triệu pixel); WEBP kiểm tra RIFF/chunk. Không upload SVG/JSP.
- Khi không chọn ảnh, giữ ảnh hiện tại. Khi lưu DB thất bại, file mới được dọn; ảnh cũ không tự xóa để tránh hỏng tham chiếu.

## Kiểm thử thủ công với SQL Server + SMTP thật

1. Register dữ liệu thiếu/sai, username trùng và email trùng; kiểm tra lỗi.
2. Register email nhận được thư; thử login trước kích hoạt, OTP sai, OTP đúng, OTP đã dùng, OTP hết hạn.
3. Gửi lại OTP (chờ 60 giây), kiểm tra mã mới; nhập sai 5 lần để kiểm tra khóa mã.
4. Forgot Password email chưa có/đã có; reset sai mã/hết hạn/đúng mã; thử mật khẩu cũ và mới.
5. Tạo ít nhất 13 Product: Home tối đa 10; danh sách lần lượt 6/6/1; thứ tự newest và detail đúng.
6. Sửa Product không chọn ảnh để giữ ảnh cũ; chọn ảnh mới; kiểm tra giá âm, tồn kho âm, Category không tồn tại.
7. Upload file rỗng, giả ảnh, đuôi không hợp lệ, ảnh hơn 5 MB; kiểm tra lỗi và không mất dữ liệu cũ.
8. Profile khi chưa login phải chuyển về Login; sửa họ tên/phone/ảnh, navbar và session cập nhật.
9. Thử xóa Category đang có Product; danh mục và dữ liệu cũ vẫn còn.
10. Logout và thử /profile; kiểm tra navbar/footer dùng chung trên mọi trang.

## SiteMesh và tài liệu

Dùng SiteMesh 3.2.1 với Jakarta Servlet 6.0 / Tomcat 10.1, cấu hình Java trong LayoutFilter và decorator /WEB-INF/decorators/main.jsp. Các thư viện Tomcat/H2/JUnit chỉ có scope test, không đóng gói vào WAR.

Nguồn tương thích: [SiteMesh 3 chính thức](https://github.com/sitemesh/sitemesh3), [Jasper Tomcat 10.1](https://tomcat.apache.org/tomcat-10.1-doc/jasper-howto.html).

Danh sách đầy đủ file tạo/sửa và cây project: [PROJECT_CHANGES.md](PROJECT_CHANGES.md).
