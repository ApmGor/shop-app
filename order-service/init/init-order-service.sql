CREATE TABLE IF NOT EXISTS "orders" (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    product_id CHAR(24) NOT NULL,
    amount INT NOT NULL,
    status VARCHAR(10) NOT NULL,
    version INT NOT NULL
);