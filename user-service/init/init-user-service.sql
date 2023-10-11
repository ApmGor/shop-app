CREATE TABLE IF NOT EXISTS "users" (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50),
    balance INT
);

CREATE TABLE IF NOT EXISTS "user_transactions" (
    id BIGSERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    amount INT,
    transaction_date TIMESTAMP
);

INSERT INTO users(name, balance)
VALUES
('sam', 1000),
('mike', 1500),
('sara', 750),
('john', 2000);