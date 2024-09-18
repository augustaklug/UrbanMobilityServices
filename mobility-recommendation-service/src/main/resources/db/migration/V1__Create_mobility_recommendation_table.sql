CREATE TABLE IF NOT EXISTS mobility_recommendation (
    id SERIAL PRIMARY KEY,
    zip_code VARCHAR(8) NOT NULL,
    recommended_mode VARCHAR(255) NOT NULL,
    timestamp TIMESTAMP NOT NULL
);