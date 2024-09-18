CREATE TABLE IF NOT EXISTS traffic_info (
    id SERIAL PRIMARY KEY,
    zip_code VARCHAR(8) NOT NULL,
    congestion_level INTEGER NOT NULL,
    timestamp TIMESTAMP NOT NULL
);