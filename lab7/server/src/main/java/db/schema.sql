-- ============================================================
-- Lab7 Schema — PostgreSQL
-- ============================================================

-- Таблица пользователей
CREATE TABLE IF NOT EXISTS users (
    id       SERIAL PRIMARY KEY,
    login    VARCHAR(64)  NOT NULL UNIQUE,
    password VARCHAR(128) NOT NULL  -- SHA-384 hex
);

-- Последовательность для id продуктов (требование: id через средства БД)
CREATE SEQUENCE IF NOT EXISTS products_id_seq START WITH 1 INCREMENT BY 1;

-- Таблица продуктов
CREATE TABLE IF NOT EXISTS products (
    id               INTEGER      PRIMARY KEY DEFAULT nextval('products_id_seq'),
    name             VARCHAR(255) NOT NULL,
    coord_x          INTEGER      NOT NULL CHECK (coord_x > -645),
    coord_y          INTEGER      NOT NULL,
    creation_date    TIMESTAMP    NOT NULL,
    price            FLOAT,
    part_number      VARCHAR(255),
    manufacture_cost FLOAT        NOT NULL,
    unit_of_measure  VARCHAR(32)  NOT NULL,
    owner_name       VARCHAR(255),
    owner_birthday   TIMESTAMP,
    owner_height     FLOAT,
    owner_passport   VARCHAR(64),
    owner_hair_color VARCHAR(32),
    owner_login      VARCHAR(64)  REFERENCES users(login) ON DELETE SET NULL
);

-- Таблица подписок
CREATE TABLE IF NOT EXISTS subscriptions (
    id           SERIAL PRIMARY KEY,
    user_login   VARCHAR(64) NOT NULL REFERENCES users(login) ON DELETE CASCADE,
    field        VARCHAR(64) NOT NULL,   -- поле для проверки: "price", "manufactureCost"
    operator     VARCHAR(8)  NOT NULL,   -- "<", ">", "<=", ">=", "=="
    threshold    FLOAT       NOT NULL,
    created_at   TIMESTAMP   NOT NULL DEFAULT now()
);
