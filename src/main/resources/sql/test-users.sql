-- Tài khoản test để đăng nhập vào hệ thống
-- Database: jakartaJPA
-- Bảng: users

INSERT INTO users (username, password, full_name)
VALUES ('admin', 'admin123', 'System Administrator');

INSERT INTO users (username, password, full_name)
VALUES ('nguyen', 'nguyen123', 'Nguyen Van A');

INSERT INTO users (username, password, full_name)
VALUES ('sinhvien', '123456', 'Student User');

-- Dữ liệu mẫu cho Category nếu cần
INSERT INTO categories (name)
VALUES ('Công nghệ');

INSERT INTO categories (name)
VALUES ('Giáo dục');

INSERT INTO categories (name)
VALUES ('Giải trí');
