INSERT INTO category (id, name, type, create_at) VALUES
(1, 'Ăn uống', 'EXPENSE', NOW()),
(2, 'Di chuyển & Xăng xe', 'EXPENSE', NOW()),
(3, 'Hóa đơn & Tiện ích', 'EXPENSE', NOW()),
(4, 'Mua sắm & Giải trí', 'EXPENSE', NOW()),
(5, 'Y tế & Sức khỏe', 'EXPENSE', NOW()),
(6, 'Lương cố định', 'INCOME', NOW()),
(7, 'Tiền thưởng & OT', 'INCOME', NOW()),
(8, 'Đầu tư & Tiết kiệm', 'INCOME', NOW()),
(9, 'Nghề phụ (Freelance)', 'INCOME', NOW())
ON CONFLICT (id) DO NOTHING;

INSERT INTO transaction (id, amount, type, description, transaction_date, category_id, created_at, updated_at) VALUES
(1, 25000000.00, 'INCOME', 'Nhận lương chuyển khoản tháng này', CURRENT_DATE - INTERVAL '15 days', 6, NOW(), NOW()),
(2, 5000000.00, 'INCOME', 'Thưởng dự án quý 2', CURRENT_DATE - INTERVAL '10 days', 7, NOW(), NOW()),
(3, 3500000.00, 'INCOME', 'Thanh toán hợp đồng Freelance Vue.js', CURRENT_DATE - INTERVAL '5 days', 9, NOW(), NOW()),
(4, 150000.00, 'EXPENSE', 'Ăn trưa cơm văn phòng', CURRENT_DATE - INTERVAL '14 days', 1, NOW(), NOW()),
(5, 500000.00, 'EXPENSE', 'Đổ xăng ô tô / xe máy', CURRENT_DATE - INTERVAL '12 days', 2, NOW(), NOW()),
(6, 1200000.00, 'EXPENSE', 'Thanh toán tiền điện nước internet', CURRENT_DATE - INTERVAL '8 days', 3, NOW(), NOW()),
(7, 2500000.00, 'EXPENSE', 'Mua sắm quần áo và đồ gia dụng', CURRENT_DATE - INTERVAL '4 days', 4, NOW(), NOW()),
(8, 350000.00, 'EXPENSE', 'Mua thuốc & khám sức khỏe định kỳ', CURRENT_DATE - INTERVAL '2 days', 5, NOW(), NOW()),
(9, 85000.00, 'EXPENSE', 'Cà phê sáng cùng đồng nghiệp', CURRENT_DATE, 1, NOW(), NOW())
ON CONFLICT (id) DO NOTHING;

SELECT setval(pg_get_serial_sequence('category', 'id'), coalesce(max(id), 1)) FROM category;
SELECT setval(pg_get_serial_sequence('transaction', 'id'), coalesce(max(id), 1)) FROM transaction;
