CREATE SEQUENCE customer_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE product_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE compliant_seq START WITH 1 INCREMENT BY 1;


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

CREATE TABLE compliant
(
    id              BIGINT,
    request_counter INT,
    text            VARCHAR(255),
    customer_id     BIGINT,
    product_id      BIGINT,
    creation_date   TIMESTAMP,
    CONSTRAINT fk_customer FOREIGN KEY (customer_id) REFERENCES customer (id),
    CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES product (id),
    CONSTRAINT unique_client_product UNIQUE (customer_id, product_id)
);