
-- V1__init_schema.sql

CREATE TABLE tbook (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255),
    author VARCHAR(255),
    price DOUBLE PRECISION,
    quantity INT,
    isbn VARCHAR(255)
);

CREATE TABLE tuser (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    surname VARCHAR(255),
    login VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    role VARCHAR(50)
);

CREATE TABLE torder (
    id SERIAL PRIMARY KEY,
    user_id INT REFERENCES tuser(id),
    date TIMESTAMP,
    state VARCHAR(50),
    total DOUBLE PRECISION
);

CREATE TABLE torderposition (
    id SERIAL PRIMARY KEY,
    book_id INT REFERENCES tbook(id),
    quantity INT
);

CREATE TABLE torder_torderposition (
    torder_id INT REFERENCES torder(id) ON DELETE CASCADE,
    positions_id INT REFERENCES torderposition(id) ON DELETE CASCADE,
    PRIMARY KEY (torder_id, positions_id)
);

CREATE TABLE tuser_torder (
    tuser_id INT REFERENCES tuser(id) ON DELETE CASCADE,
    orders_id INT REFERENCES torder(id) ON DELETE CASCADE,
    PRIMARY KEY (tuser_id, orders_id)
);
