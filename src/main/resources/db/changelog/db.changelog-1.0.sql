-- liquibase formatted sql

-- changeset alexander_ermakov:1
CREATE TABLE IF NOT EXISTS users
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(20) NOT NULL,
    second_name VARCHAR(40) NOT NULL,
    surname     VARCHAR(40) NOT NULL,
    email       VARCHAR(50) NOT NULL UNIQUE,
    role        VARCHAR(64) NOT NULL
);