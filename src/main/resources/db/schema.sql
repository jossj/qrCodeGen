-- ============================================================
-- QR Code Generator — database and table setup
-- Compatible with MySQL 8+
-- ============================================================

CREATE DATABASE IF NOT EXISTS qrcodegen
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE qrcodegen;

CREATE TABLE IF NOT EXISTS qr_codes (
    id          VARCHAR(36)     NOT NULL,
    child_name  VARCHAR(255)    NOT NULL,
    behaviour   VARCHAR(255)    NOT NULL,
    content     TEXT            NOT NULL,
    width       INT             NOT NULL DEFAULT 250,
    height      INT             NOT NULL DEFAULT 250,
    created_at  DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT pk_qr_codes PRIMARY KEY (id)
);
