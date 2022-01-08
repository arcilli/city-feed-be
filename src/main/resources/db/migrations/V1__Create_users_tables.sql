-- CREATE TYPE city as ENUM('iasi', 'cluj', 'brasov', 'bucuresti', 'timisoara', 'constanta', 'oradea', 'craiova');
-- CREATE TYPE neighborhoods as ENUM('copou', 'gara', 'centru', 'podu-ros', 'dacia', 'alexandru-cel-bun', 'tudor-vladimirescu', 'galata',
--     'cug', 'frumoasa', 'bularga', 'baza3', 'nicolina1', 'nicolina2', 'tatarasi', 'canta', 'mircea-cel-batran', 'aviatiei',
--     'zona-industriala', 'moara-de-vant', 'cantemir', 'uzinei', 'ticau', 'agronomiei', 'tesatura', 'podu-de-fier', 'tg-cucu',
--     'pacurari', 'manta-rosie', 'dancu', 'bucsinescu', 'podu-de-piatra', 'bucium', 'socola', 'sararie', 'metalurgie'
--     );

CREATE OR REPLACE FUNCTION set_update_date() RETURNS TRIGGER AS $set_update_date$
BEGIN
    NEW.update_date := now();
    RETURN NEW;
END
$set_update_date$ LANGUAGE plpgsql;

CREATE TABLE IF NOT EXISTS users_credentials (
    id      UUID PRIMARY KEY,
    username TEXT NOT NULL ,
    email_address TEXT NOT NULL,
    password TEXT NOT NULL,
    update_date TIMESTAMPTZ
);

CREATE TABLE IF NOT EXISTS users_info(
    id UUID PRIMARY KEY,
    full_name VARCHAR,
    city VARCHAR,
    home_address TEXT,
    neighborhood VARCHAR,
    credentials_key UUID references users_credentials(id),
    created_date TIMESTAMPTZ NOT NULL DEFAULT now(),
    update_date TIMESTAMPTZ
);

CREATE TRIGGER set_update_date_users
    BEFORE UPDATE ON users_info
    FOR EACH ROW
EXECUTE PROCEDURE set_update_date();
