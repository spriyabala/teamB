CREATE TABLE IF NOT EXISTS books (
    id SERIAL PRIMARY KEY,
    name VARCHAR(32),
    author VARCHAR(100),
    created timestamp
);