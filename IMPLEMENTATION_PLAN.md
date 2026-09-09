# Kế hoạch hoàn thiện yêu cầu giảng viên

## Hiện trạng đã kiểm tra

- [x] Đọc pom.xml, Java, resources, webapp, test, .gitignore, .env.example, README.
- [x] Git ban đầu sạch; main tại 53d500d; đọc 15 commit gần nhất (repository có 6 commit).
- [x] Test nền: 21 tests, 0 failures, 0 errors, BUILD SUCCESS.
- [x] Xác định kiến trúc: Java 17 release, Maven WAR, Servlet/Service/DAO/JPA,
  Hibernate 6.5.3, SQL Server, JSP/JSTL, SiteMesh 3.2.1, Tomcat 10.1.
- [x] Sửa độc lập: validation login, không phản chiếu OTP, cooldown khi mail lỗi,
  loại trừ /js/* khỏi SiteMesh; bổ sung test hồi quy.
- [x] Script tạo database an toàn và danh sách biến môi trường mẫu.

## Các giai đoạn còn phải triển khai tuần tự

- [ ] Boot 4: dependency, entry point, WAR, MVC controller, Spring Data repositories,
  transaction, JSP trong /WEB-INF/views, view resolver, datasource từ môi trường.
- [ ] Chạy test thay thế thành công trước khi loại bỏ JpaConfig/persistence.xml/DAO cũ.
- [ ] Role USER/ADMIN, principal tối giản, kiểm tra quyền server /admin/**,
  một cơ chế CSRF, logout POST, cập nhật quyền từ database.
- [ ] Admin Category CRUD/search, validation, ngăn xóa khi có Product tham chiếu.
- [ ] Admin User CRUD/search/phân trang, chống trùng, bảo vệ admin cuối cùng/tự xóa.
- [ ] Admin Product CRUD/multipart; public Home/list/detail và phân trang DB 6.
- [ ] OTP registration/reset: test database transaction và cạnh tranh, không chỉ mock.
- [ ] Profile và upload: decode WEBP thật (hiện chỉ kiểm tra container), test cleanup.
- [ ] SiteMesh Jakarta với Boot 4, tất cả JSP render một decorator; menu theo role.
- [ ] Seeder opt-in, chạy hai lần không trùng, 4 Category + 13 Product,
  tài khoản demo/admin băm mật khẩu và role đúng; không chiếm tài khoản cũ trùng tên.
- [ ] Đủ 18 nhóm test yêu cầu, mvn test sau mỗi giai đoạn, commit riêng khi xanh.
- [ ] mvn clean package, SQL Server thật, restart/persistence, OTP SMTP thật.
- [ ] README cuối cùng, báo cáo nhóm test/commit/Git và checklist trình duyệt.

## Trở ngại môi trường đã xác minh

Maven mặc định dùng C:\.m2\repository không ghi được. Dùng cache trong workspace:

```powershell
mvn "-Dmaven.repo.local=.build-cache/repository" clean test
mvn "-Dmaven.repo.local=.build-cache/repository" clean package
```

.build-cache đã được ignore; không commit JAR/cache. Cache này được sao chép từ
cache Maven hiện có để chạy project gốc trong sandbox.

Tải spring-boot-starter-parent:4.0.3 từ Maven Central thất bại:
`Permission denied: getsockopt`. Không tìm thấy Spring Boot trong cache hiện có.
Cần phiên cho phép Maven truy cập mạng hoặc cache đầy đủ dependency Boot 4.
Không thay pom sang dependency chưa tải được rồi tuyên bố migration thành công.

DB_URL, DB_USERNAME, DB_PASSWORD, MAIL_HOST, MAIL_PORT, MAIL_USERNAME,
MAIL_PASSWORD, MAIL_FROM, UPLOAD_DIR và SEED_SAMPLE_DATA đều chưa được đặt
trong tiến trình kiểm thử. Chưa chạy SQL/SMTP thật; không đọc hay in mật khẩu.

## Các điểm bảo toàn khi migration

Giữ ddl-auto=update; không create/create-drop ở runtime. Giữ naming cột hiện hữu
(createdAt, activationOtp..., full_name), filtered unique index email SQL Server,
version và các URL public. Không tự gán role ADMIN cho tài khoản cũ chỉ vì tên admin.
Không bỏ CSRF trước khi cơ chế thay thế được test. Không đổi Tomcat 10.1 sang chạy
WAR Boot 4 mà chưa nâng container lên Servlet 6.1/Tomcat 11.

Nguồn chính thức đã kiểm tra:
- https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-4.0-Migration-Guide
- https://github.com/sitemesh/sitemesh3 (ma trận SiteMesh 3.3.x/Boot 4;
  3.3.0-RC1 là release candidate, cần quyết định phiên bản và kiểm thử thực tế).

## Git sau khi hoàn thành (chỉ thực hiện khi người dùng xác nhận)

Tạo repository GitHub rỗng, không khởi tạo README/license. Thêm remote mới bằng
`git remote add submission <URL>` rồi push các branch và tag cần giữ bằng
`git push submission --all` và `git push submission --tags`. Các commit giữ nguyên
hash/lịch sử; không amend, rebase, backdate hay force push. Nếu cần toàn bộ nhánh
chỉ tồn tại trên remote cũ, kiểm kê refs trước rồi mới chọn quy trình mirror.
Chưa tạo repository, push hoặc nộp UTeLMS.
## Kết quả giai đoạn độc lập

`mvn -Dmaven.repo.local=.build-cache/repository test`: BUILD SUCCESS,
24 tests, 0 failures, 0 errors, 0 skipped.
JSP: 1; Product JPA: 3; Upload: 3; User/OTP: 11; HTTP runtime: 6.

Sau test, `git add` bị chặn: không tạo được `.git/index.lock` (Permission denied).
Chưa tạo được commit nào. Sandbox hiện chỉ cho đọc .git; cần phiên cho phép ghi
metadata Git để commit từng nhóm đã kiểm thử. Không push hoặc rewrite lịch sử.

mvn -Dmaven.repo.local=.build-cache/repository clean package: BUILD SUCCESS; 24 tests, 0 failures, 0 errors, 0 skipped. WAR: target/BT25-08-2026.war. Đây là WAR Servlet hiện tại, chưa phải Boot 4.
