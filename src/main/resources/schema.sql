DROP TABLE  IF EXISTS book;
CREATE TABLE book (id IDENTITY PRIMARY KEY, name VARCHAR(200) NOT NULL,
    description VARCHAR(255), author VARCHAR(200), type VARCHAR(100) NOT NULL, price DECIMAL(20,2),
    isbn VARCHAR(200));
