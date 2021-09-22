CREATE TABLE foods
(
    id         INTEGER,
    name       VARCHAR(280) NOT NULL,
    price      DOUBLE PRECISION NOT NULL,
    created_at TIMESTAMP        NOT NULL,
    updated_at TIMESTAMP        NOT NULL,

    CONSTRAINT foods_cars PRIMARY KEY (id)
);

CREATE SEQUENCE foods_id_sequence START 1 INCREMENT 1 OWNED BY foods.id;