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
