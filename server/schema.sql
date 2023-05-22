CREATE DATABASE IF NOT EXISTS testdb;

USE testdb;

CREATE TABLE IF NOT EXISTS users (
  id BIGINT NOT NULL AUTO_INCREMENT,
  username VARCHAR(20) UNIQUE,
  email VARCHAR(50) UNIQUE,
  password VARCHAR(120),
  PRIMARY KEY (id)
  );

CREATE TABLE IF NOT EXISTS roles (
    id INTEGER NOT NULL AUTO_INCREMENT,
    name VARCHAR(20) ,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS user_roles (
  user_id BIGINT NOT NULL,
  role_id INT NOT NULL,
  PRIMARY KEY (user_id, role_id),
  FOREIGN KEY (user_id) REFERENCES users (id),
  FOREIGN KEY (role_id) REFERENCES roles (id)
);

INSERT INTO roles(name) VALUES('ROLE_USER');
INSERT INTO roles(name) VALUES('ROLE_ADMIN');


CREATE TABLE saved_recipes (
  id INT PRIMARY KEY AUTO_INCREMENT,
  user_id BIGINT,
  recipe_id BIGINT,
  recipe_title VARCHAR(255),
  FOREIGN KEY (user_id) REFERENCES users(id),
);

CREATE TABLE IF NOT EXISTS recipe_details (
  saved_recipe_id INT PRIMARY KEY,
  servings INT,
  ready_in_minutes INT,
  FOREIGN KEY (saved_recipe_id) REFERENCES saved_recipes(id)
);

INSERT INTO saved_recipes(user_id, recipe_id, recipe_title) VALUES('24', '662584', 'Sweet Potato Kimchi Hash Brunch - gluten free, dairy free, vegetarian');