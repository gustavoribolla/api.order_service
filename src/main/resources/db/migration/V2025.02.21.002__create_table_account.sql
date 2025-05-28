CREATE TABLE orders (
    id_order VARCHAR(36) NOT NULL,
    dt_date DATE NOT NULL,
    nr_total DECIMAL(10, 2),
    id_account VARCHAR(64) NOT NULL,
    CONSTRAINT pk_order PRIMARY KEY (id_order)
);
