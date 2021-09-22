CREATE TABLE shops
(
    id         INTEGER,
    user_id    INTEGER,
    name       VARCHAR(280),
    type       VARCHAR(150)     NOT NULL,
    price      DOUBLE PRECISION NOT NULL,
    created_at TIMESTAMP        NOT NULL,
    updated_at TIMESTAMP        NOT NULL,


    CONSTRAINT pk_shops PRIMARY KEY (id),
    CONSTRAINT fk_shops FOREIGN KEY (user_id) REFERENCES users(id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE SEQUENCE shops_id_sequence START 1 INCREMENT 1 OWNED BY shops.id;