CREATE DATABASE eFashion;
USE eFashion;
CREATE TABLE IF NOT EXISTS roles (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(100),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS users (
    id INT NOT NULL AUTO_INCREMENT,
    email VARCHAR(100) NOT NULL,
    phone INT NOT NULL,
    password VARCHAR(100) NOT NULL,
    fullname VARCHAR(100) NOT NULL,
    avatar VARCHAR(100),
    role_id INT NOT NULL,
    PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS products (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    price DOUBLE NOT NULL,
    image VARCHAR(100),
    description VARCHAR(100),
    quantity INT NOT NULL,
    cate_id INT NOT NULL,
    status VARCHAR(50),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS rating_product (
    id INT NOT NULL AUTO_INCREMENT,
    user_id INT NOT NULL,
    product_id INT NOT NULL,
    star INT,
    comment VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS category (
    id INT NOT NULL AUTO_INCREMENT,
	name VARCHAR(50) NOT NULL,
	description VARCHAR(100),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS orders (
    id INT NOT NULL AUTO_INCREMENT,
    user_id INT NOT NULL,
	total_price DOUBLE,
    create_date DATE,
    status VARCHAR(50),
    PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS order_item (
	id INT NOT NULL AUTO_INCREMENT,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
	user_id INT NOT NULL,
    PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS place_order (
	id INT NOT NULL AUTO_INCREMENT,
    order_item_id INT NOT NULL,
    user_id INT NOT NULL,
    PRIMARY KEY (id)
);

ALTER TABLE users ADD FOREIGN KEY (role_id) REFERENCES roles (id)  ON DELETE CASCADE;
ALTER TABLE products ADD FOREIGN KEY (cate_id) REFERENCES category (id)  ON DELETE CASCADE;
ALTER TABLE rating_product ADD FOREIGN KEY (user_id) REFERENCES users (id)  ON DELETE CASCADE;
ALTER TABLE rating_product ADD FOREIGN KEY (product_id) REFERENCES products (id)  ON DELETE CASCADE;
ALTER TABLE orders ADD FOREIGN KEY (user_id) REFERENCES users (id)  ON DELETE CASCADE;
ALTER TABLE order_item ADD FOREIGN KEY (product_id) REFERENCES products (id)  ON DELETE CASCADE;
ALTER TABLE place_order ADD FOREIGN KEY (order_item_id) REFERENCES order_item (id)  ON DELETE CASCADE;
ALTER TABLE place_order ADD FOREIGN KEY (user_id) REFERENCES users (id)  ON DELETE CASCADE;
ALTER TABLE order_item ADD FOREIGN KEY (user_id) REFERENCES users (id)  ON DELETE CASCADE;