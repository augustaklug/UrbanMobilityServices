CREATE TABLE IF NOT EXISTS traffic_info (
    id SERIAL PRIMARY KEY,
    zip_code VARCHAR(8) NOT NULL,
    congestion_level INTEGER NOT NULL,
    timestamp TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS mobility_recommendation (
    id SERIAL PRIMARY KEY,
    zip_code VARCHAR(8) NOT NULL,
    recommended_mode VARCHAR(50) NOT NULL,
    timestamp TIMESTAMP NOT NULL
);