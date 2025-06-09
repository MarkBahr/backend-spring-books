/*
Project: Books Inventory Management with JWT Authentication
Author: Mark Bahr
*/


-- CREATE __ TABLE
-- CREATE SEQUENCE FOR ___ TABLE


/* ---------------------------------------------------
            ROLE Table and Data
-----------------------------------------------------*/
CREATE TABLE roles(
    role_id SERIAL PRIMARY KEY,
    role_name VARCHAR(20)
);

INSERT INTO roles(name) VALUES('ROLE_USER');
INSERT INTO roles(name) VALUES('ROLE_ADMIN');

/* ---------------------------------------------------
            USERS Table and Data
-----------------------------------------------------*/
CREATE TABLE users(
    user_id SERIAL PRIMARY KEY,
    username VARCHAR(30) UNIQUE,
    email VARCHAR(50) UNIQUE,
    password VARCHAR(120),
    account_non_locked BOOLEAN,

);

INSERT INTO users (username, email, password) VALUES ('johnsmith', 'js@test.com', 'Password1');

/* ---------------------------------------------------
            ENUM ROLE Table and Data
-----------------------------------------------------*/
CREATE TABLE user_roles(
    user_id INTEGER,
    role_id INTEGER,
    -- constraint name follows convention fk_<source_table>_<target_table>_<column>
    CONSTRAINT fk_user_roles_users_user_id FOREIGN KEY (user_id) 
        REFERENCES users (user_id) ON DELETE CASCADE,
    CONSTRAINT fk_user_roles_roles_role_id FOREIGN KEY (role_id)
        REFERENCES roles (role_id) ON DELETE CASCADE
);


/* ---------------------------------------------------
            notes Table and Data
-----------------------------------------------------*/
