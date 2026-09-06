-- Dữ liệu mẫu 10 sản phẩm có ảnh sẵn cho database: jakartaJPA
USE jakartaJPA;
GO

-- 1. Đảm bảo Categories tồn tại
IF NOT EXISTS (SELECT 1 FROM categories WHERE name = N'Điện thoại')
    INSERT INTO categories (name) VALUES (N'Điện thoại');
IF NOT EXISTS (SELECT 1 FROM categories WHERE name = N'Laptop')
    INSERT INTO categories (name) VALUES (N'Laptop');
IF NOT EXISTS (SELECT 1 FROM categories WHERE name = N'Âm thanh')
    INSERT INTO categories (name) VALUES (N'Âm thanh');
IF NOT EXISTS (SELECT 1 FROM categories WHERE name = N'Đồng hồ')
    INSERT INTO categories (name) VALUES (N'Đồng hồ');
IF NOT EXISTS (SELECT 1 FROM categories WHERE name = N'Phụ kiện')
    INSERT INTO categories (name) VALUES (N'Phụ kiện');
IF NOT EXISTS (SELECT 1 FROM categories WHERE name = N'Màn hình')
    INSERT INTO categories (name) VALUES (N'Màn hình');
IF NOT EXISTS (SELECT 1 FROM categories WHERE name = N'Máy tính bảng')
    INSERT INTO categories (name) VALUES (N'Máy tính bảng');

DECLARE @catPhone INT = (SELECT TOP 1 id FROM categories WHERE name = N'Điện thoại');
DECLARE @catLaptop INT = (SELECT TOP 1 id FROM categories WHERE name = N'Laptop');
DECLARE @catAudio INT = (SELECT TOP 1 id FROM categories WHERE name = N'Âm thanh');
DECLARE @catWatch INT = (SELECT TOP 1 id FROM categories WHERE name = N'Đồng hồ');
DECLARE @catAcc INT = (SELECT TOP 1 id FROM categories WHERE name = N'Phụ kiện');
DECLARE @catDisplay INT = (SELECT TOP 1 id FROM categories WHERE name = N'Màn hình');
DECLARE @catTablet INT = (SELECT TOP 1 id FROM categories WHERE name = N'Máy tính bảng');

-- 2. Chèn 10 sản phẩm mẫu có sẵn ảnh đã tạo sẵn
INSERT INTO products (name, description, price, stock, image, createdAt, category_id)
VALUES
(N'iPhone 15 Pro Max', N'Titanium tự nhiên, chip A17 Pro mạnh mẽ, camera tiềm vọng zoom 5x sắc nét.', 29990000.00, 25, '11111111-1111-1111-1111-111111111101.png', DATEADD(MINUTE, -100, GETDATE()), @catPhone),
(N'MacBook Pro M3 14 inch', N'Màn hình Liquid Retina XDR, chip Apple M3 siêu tiết kiệm pin, RAM 16GB.', 39990000.00, 15, '11111111-1111-1111-1111-111111111102.png', DATEADD(MINUTE, -90, GETDATE()), @catLaptop),
(N'Tai nghe Sony WH-1000XM5', N'Chống ồn chủ động đỉnh cao, âm thanh Hi-Res Audio, thời lượng pin 30 giờ.', 6990000.00, 40, '11111111-1111-1111-1111-111111111103.png', DATEADD(MINUTE, -80, GETDATE()), @catAudio),
(N'Apple Watch Series 9 GPS', N'Màn hình Always-On sáng gấp đôi, tính năng Double Tap tiện lợi, theo dõi sức khỏe.', 8990000.00, 30, '11111111-1111-1111-1111-111111111104.png', DATEADD(MINUTE, -70, GETDATE()), @catWatch),
(N'Bàn phím cơ Keychron Q1 Pro', N'Vỏ nhôm CNC nguyên khối, kết nối Bluetooth & Type-C, hot-swap switch cao cấp.', 3490000.00, 50, '11111111-1111-1111-1111-111111111105.png', DATEADD(MINUTE, -60, GETDATE()), @catAcc),
(N'Chuột không dây Logitech MX Master 3S', N'Cảm biến 8K DPI mọi bề mặt, con lăn MagSpeed siêu nhanh và êm ái.', 2190000.00, 60, '11111111-1111-1111-1111-111111111106.png', DATEADD(MINUTE, -50, GETDATE()), @catAcc),
(N'Màn hình Dell UltraSharp U2724D', N'Độ phân giải 2K 120Hz, tấm nền IPS Black độ tương phản cao, cổng Type-C 90W.', 11500000.00, 18, '11111111-1111-1111-1111-111111111107.png', DATEADD(MINUTE, -40, GETDATE()), @catDisplay),
(N'Loa Bluetooth JBL Charge 5', N'Âm thanh JBL Pro Sound uy lực, chống nước IP67, kiêm sạc dự phòng cho điện thoại.', 3290000.00, 35, '11111111-1111-1111-1111-111111111108.png', DATEADD(MINUTE, -30, GETDATE()), @catAudio),
(N'iPad Air M2 11 inch Wi-Fi', N'Sức mạnh từ chip M2, camera trước Ultra Wide ngang, hỗ trợ Apple Pencil Pro.', 16490000.00, 20, '11111111-1111-1111-1111-111111111109.png', DATEADD(MINUTE, -20, GETDATE()), @catTablet),
(N'Balo Laptop Gaming chống nước', N'Chất liệu vải Oxford chống thấm, ngăn đựng laptop 15.6 inch chống sốc dày dặn.', 890000.00, 80, '11111111-1111-1111-1111-111111111110.png', DATEADD(MINUTE, -10, GETDATE()), @catAcc);

-- 3. Cập nhật ảnh đại diện cho các tài khoản test
UPDATE users SET image = '11111111-1111-1111-1111-111111111199.png' WHERE username IN ('admin', 'nguyen', 'sinhvien');
