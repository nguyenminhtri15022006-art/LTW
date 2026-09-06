-- Reference only. Hibernate/JPA (hbm2ddl.auto=update) manages the application schema.
-- Run only against the intended exercise database, if you choose to manage DDL manually.
-- Existing users/categories are not dropped or replaced.
IF OBJECT_ID(N'products', N'U') IS NULL
BEGIN
    CREATE TABLE products (
        id BIGINT IDENTITY(1,1) NOT NULL PRIMARY KEY,
        name VARCHAR(255) NOT NULL,
        description VARCHAR(4000) NULL,
        price DECIMAL(18,2) NOT NULL,
        stock INT NOT NULL,
        image VARCHAR(255) NULL,
        createdAt DATETIME2(6) NOT NULL,
        category_id INT NOT NULL,
        CONSTRAINT FK_products_categories FOREIGN KEY (category_id) REFERENCES categories(id)
    );
    CREATE INDEX IX_products_newest ON products(createdAt, id);
END;
-- For email uniqueness with multiple legacy NULL email rows:
-- CREATE UNIQUE INDEX UX_users_email ON users(email) WHERE email IS NOT NULL;
