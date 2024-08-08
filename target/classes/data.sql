CREATE TABLE admin (
    id BIGINT(20) PRIMARY KEY NOT NULL,
    is_enabled BIT(1) NOT NULL,
    gender ENUM('FEMALE', 'MALE', 'OTHER') COLLATE utf8mb4_general_ci NULL,
    lastname VARCHAR(255) COLLATE utf8mb4_general_ci NOT NULL,
    name VARCHAR(255) COLLATE utf8mb4_general_ci NOT NULL
);


CREATE TABLE customer (
    id BIGINT(20) PRIMARY KEY NOT NULL,
    is_enabled BIT(1) NOT NULL,
    gender ENUM('FEMALE', 'MALE', 'OTHER') COLLATE utf8mb4_general_ci NULL,
    lastname VARCHAR(255) COLLATE utf8mb4_general_ci NOT NULL,
    name VARCHAR(255) COLLATE utf8mb4_general_ci NOT NULL,
    address VARCHAR(255) COLLATE utf8mb4_general_ci NULL,
    balance FLOAT NULL,
    birth_date DATE NULL,
    email VARCHAR(255) COLLATE utf8mb4_general_ci NOT NULL,
    verification_code VARCHAR(64) COLLATE utf8mb4_general_ci NULL,
    UNIQUE KEY UKcshnqhp3rw620qyrj2454af1w (name, lastname)
);


CREATE TABLE user_credentials (
    id BIGINT(20) PRIMARY KEY AUTO_INCREMENT NOT NULL,
    password VARCHAR(255) COLLATE utf8mb4_general_ci NULL,
    role ENUM('ADMIN', 'CUSTOMER', 'MOD') COLLATE utf8mb4_general_ci NULL,
    username VARCHAR(255) COLLATE utf8mb4_general_ci NOT NULL,
    person_id BIGINT(20) NULL,
    UNIQUE KEY UK8pcilw7ay5rs8c4dtrtik21pw (username)
);


CREATE TABLE book (
    id BIGINT(20) PRIMARY KEY AUTO_INCREMENT NOT NULL,
    author VARCHAR(255) COLLATE utf8mb4_general_ci NOT NULL,
    isbn VARCHAR(255) COLLATE utf8mb4_general_ci NOT NULL,
    name VARCHAR(255) COLLATE utf8mb4_general_ci NOT NULL,
    page_number INT(11) NOT NULL,
    price FLOAT NOT NULL,
    publication_date DATE NOT NULL,
    publisher VARCHAR(255) COLLATE utf8mb4_general_ci NOT NULL,
    stock INT(11) NOT NULL,
    UNIQUE KEY UKehpdfjpu1jm3hijhj4mm0hx9h (isbn),
    UNIQUE KEY UKwugryet8mf6oi28n00x2eoc4 (name)
);


INSERT INTO customer (id, is_enabled, gender, lastname, name, address, balance, birth_date, email, verification_code)
SELECT 2, b'1', 'MALE', 'Doe', 'John', '123 Main St', 100.50, '1990-01-01', 'john.doe@example.com', NULL
WHERE NOT EXISTS (SELECT 1 FROM customer WHERE id = 2);

INSERT INTO customer (id, is_enabled, gender, lastname, name, address, balance, birth_date, email, verification_code)
SELECT 3, b'1', 'FEMALE', 'Smith', 'Jane', '456 Oak St', 150.75, '1985-05-15', 'jane.smith@example.com', NULL
WHERE NOT EXISTS (SELECT 1 FROM customer WHERE id = 3);

INSERT INTO customer (id, is_enabled, gender, lastname, name, address, balance, birth_date, email, verification_code)
SELECT 4, b'1', 'OTHER', 'Taylor', 'Alex', '789 Pine St', 200.00, '2000-10-20', 'alex.taylor@example.com', NULL
WHERE NOT EXISTS (SELECT 1 FROM customer WHERE id = 4);

INSERT INTO admin (id, is_enabled, gender, lastname, name)
SELECT 1, b'1', 'MALE', 'Karanalbant', 'Kaan'
WHERE NOT EXISTS (SELECT 1 FROM admin WHERE id = 1);

INSERT INTO user_credentials (id, password, role, username, person_id)
SELECT 1, '$2a$10$R0.UcM02S9rfhpkVDkNOVerAvfILQEMsvCH0nSn/jHAwBX7jf9/YO', 'ADMIN', 'kaan.karanalbant', 1
WHERE NOT EXISTS (SELECT 1 FROM user_credentials WHERE id = 1);

INSERT INTO user_credentials (id, password, role, username, person_id)
SELECT 2, '$2a$10$R0.UcM02S9rfhpkVDkNOVerAvfILQEMsvCH0nSn/jHAwBX7jf9/YO', 'CUSTOMER', 'john.doe', 2
WHERE NOT EXISTS (SELECT 1 FROM user_credentials WHERE id = 2);

INSERT INTO user_credentials (id, password, role, username, person_id)
SELECT 3, '$2a$10$R0.UcM02S9rfhpkVDkNOVerAvfILQEMsvCH0nSn/jHAwBX7jf9/YO', 'CUSTOMER', 'jane.smith', 3
WHERE NOT EXISTS (SELECT 1 FROM user_credentials WHERE id = 3);

INSERT INTO user_credentials (id, password, role, username, person_id)
SELECT 4, '$2a$10$R0.UcM02S9rfhpkVDkNOVerAvfILQEMsvCH0nSn/jHAwBX7jf9/YO', 'CUSTOMER', 'alex.taylor', 4
WHERE NOT EXISTS (SELECT 1 FROM user_credentials WHERE id = 4);

INSERT INTO book (author, isbn, name, page_number, price, publication_date, publisher, stock)
SELECT 'George Orwell', '978-0451524935', '1984', 328, 9.99, '1949-06-08', 'Secker & Warburg', 100
WHERE NOT EXISTS (SELECT 1 FROM book WHERE isbn = '978-0451524935');

INSERT INTO book (author, isbn, name, page_number, price, publication_date, publisher, stock)
SELECT 'Harper Lee', '978-0060935467', 'To Kill a Mockingbird', 336, 7.99, '1960-07-11', 'J.B. Lippincott & Co.', 150
WHERE NOT EXISTS (SELECT 1 FROM book WHERE isbn = '978-0060935467');

INSERT INTO book (author, isbn, name, page_number, price, publication_date, publisher, stock)
SELECT 'F. Scott Fitzgerald', '978-0743273565', 'The Great Gatsby', 180, 10.99, '1925-04-10', 'Charles Scribner\'s Sons', 200
WHERE NOT EXISTS (SELECT 1 FROM book WHERE isbn = '978-0743273565');

INSERT INTO book (author, isbn, name, page_number, price, publication_date, publisher, stock)
SELECT 'J.K. Rowling', '978-0439139601', 'Harry Potter and the Goblet of Fire', 734, 29.99, '2000-07-08', 'Bloomsbury', 250
WHERE NOT EXISTS (SELECT 1 FROM book WHERE isbn = '978-0439139601');

INSERT INTO book (author, isbn, name, page_number, price, publication_date, publisher, stock)
SELECT 'J.R.R. Tolkien', '978-0618640157', 'The Hobbit', 310, 14.99, '1937-09-21', 'George Allen & Unwin', 300
WHERE NOT EXISTS (SELECT 1 FROM book WHERE isbn = '978-0618640157');
