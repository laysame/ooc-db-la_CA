CREATE DATABASE IF NOT EXISTS `system_solver`;

USE `system_solver`;

CREATE TABLE IF NOT EXISTS `account_type` (
   account_type_id INT NOT NULL PRIMARY KEY,
   description VARCHAR(128) NOT NULL
);

CREATE TABLE IF NOT EXISTS `user` (
    user_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(128) NOT NULL UNIQUE,
    password VARCHAR(40) NOT NULL,
    first_name VARCHAR(128) NOT NULL,
    last_name VARCHAR(128) NOT NULL,
    account_type_id INT NOT NULL,
    created_on DATETIME NOT NULL,
    FOREIGN KEY(account_type_id) REFERENCES account_type(account_type_id)
);

CREATE TABLE IF NOT EXISTS `operation` (
   operation_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
   user_id INT NOT NULL,
   first_equation VARCHAR(128) NOT NULL,
   second_equation VARCHAR(128) NOT NULL,
   third_equation VARCHAR(128),
   x_value DOUBLE NOT NUll,
   y_value DOUBLE NOT NUll,
   z_value DOUBLE,
   created_on DATETIME NOT NULL,
   FOREIGN KEY(user_id) REFERENCES user(user_id)
);


INSERT INTO `account_type`(account_type_id, description)
VALUES
(1, "ADMIN"),
(2, "REGULAR");

INSERT INTO `user`(user_id, username, password, first_name, last_name, account_type_id, created_on)
VALUES
(
    1,
    "CCT",
    "C4A265CA6DC31ED4A47A6047E92C5E3790135AF9",
    "Sam",
    "Weiss",
    1,
    CURRENT_TIME
),
(
    2,
    "John101",
    "C4A265CA6DC31ED4A47A6047E92C5E3790135AF9",
    "John",
    "Doe",
    2,
    CURRENT_TIME
),
(
    3,
    "Jane102",
    "C4A265CA6DC31ED4A47A6047E92C5E3790135AF9",
    "Jane",
    "Doe",
    2,
    CURRENT_TIME
);


INSERT INTO `operation`(operation_id, user_id, first_equation, second_equation, third_equation, x_value, y_value, z_value, created_on)
VALUES
(
    1,
    2,
    "2x + 5y = 3",
    "7x - 2y = 0",
    NULL,
    5,
    2,
    NULL,
    CURRENT_TIME
),
(
    2,
    2,
    "10x + 15y = 7",
    "3x - 6y = 2",
    NULL,
    2,
    9,
    NULL,
    CURRENT_TIME
),
(
    3,
    2,
    "3x + 8y = 1",
    "7x - 8y = 11",
    NULL,
    5,
    2,
    NULL,
    CURRENT_TIME
);