-- Run in SSMS with an account permitted to create a database.
-- Safe to run repeatedly. Does not drop tables or delete data.
USE master;
GO
IF DB_ID(N'jakartaJPA') IS NULL
BEGIN
    EXEC(N'CREATE DATABASE [jakartaJPA]');
END;
GO
-- SQL authentication setup (manual, through SSMS):
-- 1. Security > Logins > New Login > SQL Server authentication.
-- 2. Choose your own login and strong password; never save the password in Git.
-- 3. User Mapping: map that login to jakartaJPA.
-- 4. Grant db_datareader/db_datawriter and the DDL permissions required by
--    Hibernate schema update (db_ddladmin for the local exercise database).
-- 5. Set DB_USERNAME and DB_PASSWORD on the application process.
--    Do not put these values in this script or application source.
-- DB_URL example:
-- jdbc:sqlserver://localhost:1433;databaseName=jakartaJPA;encrypt=true;trustServerCertificate=true
-- After the application has created/updated its tables, verify manually:
-- USE jakartaJPA;
-- SELECT COUNT(*) FROM users;
-- SELECT COUNT(*) FROM categories;
-- SELECT COUNT(*) FROM products;