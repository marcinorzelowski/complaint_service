CREATE SEQUENCE customer_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE product_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE complaint_seq START WITH 1 INCREMENT BY 1;


CREATE TABLE IF NOT EXISTS customer
(
    id         BIGINT,
    first_name VARCHAR(100),
    last_name  VARCHAR(100),
    email      VARCHAR(100) UNIQUE,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS product
(
    id   BIGINT,
    name VARCHAR(100),
    PRIMARY KEY (id)
);

CREATE TABLE complaint
(
    id              BIGINT,
    request_counter INT       DEFAULT 1,
    text            VARCHAR(255),
    author_id       BIGINT,
    product_id      BIGINT,
    creation_date   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_customer FOREIGN KEY (author_id) REFERENCES customer (id),
    CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES product (id),
    CONSTRAINT unique_client_product UNIQUE (author_id, product_id)
);