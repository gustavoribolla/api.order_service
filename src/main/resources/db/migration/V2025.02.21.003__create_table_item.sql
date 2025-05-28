CREATE TABLE item (
    id_item VARCHAR(36) NOT NULL,
    nr_quantity INT NOT NULL,
    nr_total DECIMAL(10, 2),
    id_order VARCHAR(64) NOT NULL,
    id_product VARCHAR(64) NOT NULL,
    CONSTRAINT pk_item PRIMARY KEY (id_item)
);
