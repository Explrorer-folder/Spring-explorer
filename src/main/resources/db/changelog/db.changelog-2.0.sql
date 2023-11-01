--liquibase formatted sql

--changeset barabanov:1
ALTER TABLE users
    ADD COLUMN created_at TIMESTAMP;

ALTER TABLE users
    ADD COLUMN modified_at TIMESTAMP;

ALTER TABLE users
    ADD COLUMN created_by VARCHAR(32);

ALTER TABLE users
    ADD COLUMN last_modified_by VARCHAR(32);
