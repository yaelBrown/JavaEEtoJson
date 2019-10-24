CREATE DATABASE messages;

USE messages;

CREATE TABLE msg(
	id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    author VARCHAR(255),
    message TEXT
);

DESCRIBE msg;

INSERT INTO msg (author, message) VALUE ("Yael", "Hello there =)");

SELECT * FROM msg;