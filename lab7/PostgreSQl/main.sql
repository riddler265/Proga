

CREATE TYPE unitOfMeasure AS ENUM ('METERS', 'SQUARE_METERS', 'LITERS', 'GRAMS');
CREATE TYPE color AS ENUM ('GREEN', 'RED', 'BLACK', 'YELLOW', 'ORANGE');

CREATE TABLE IF NOT EXISTS coordinates (
    id SERIAL PRIMARY KEY,
    x INTEGER NOT NULL CHECK (x > -645),
    y INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS person (
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(255) NOT NULL UNIQUE,
    password    VARCHAR(128) NOT NULL, 
    birthday    TIMESTAMP,
    height      FLOAT NOT NULL CHECK (height > 0),
    passport_id VARCHAR(255),
    hair_color  color
);

CREATE TABLE IF NOT EXISTS product (
    id SERIAL PRIMARY KEY,
    name             VARCHAR(255) NOT NULL,
    coordinates_id   INTEGER NOT NULL REFERENCES coordinates(id),
    creation_date    TIMESTAMP NOT NULL DEFAULT NOW(),
    price            FLOAT CHECK (price > 0),
    part_number      VARCHAR(255),
    manufacture_cost FLOAT NOT NULL,
    unit_of_measure  unitOfMeasure NOT NULL,
    owner_id         INTEGER REFERENCES person(id)
);

CREATE TYPE subscription_field AS ENUM (
    'PRICE',
    'MANUFACTURE_COST',
    'NAME',
    'UNIT_OF_MEASURE',
    'PART_NUMBER'
);

CREATE TYPE subscription_condition AS ENUM (
    'LESS',
    'GREATER',
    'EQUAL',
    'NOT_EQUAL'
);

CREATE TABLE IF NOT EXISTS subscriptions (
    id        SERIAL PRIMARY KEY,
    person_id INTEGER NOT NULL REFERENCES person(id),
    field     subscription_field NOT NULL,
    condition subscription_condition NOT NULL,
    value     VARCHAR(255)
);

