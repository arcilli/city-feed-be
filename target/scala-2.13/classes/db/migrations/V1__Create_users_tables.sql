CREATE SEQUENCE user_id_seq;

CREATE OR REPLACE FUNCTION set_update_date() RETURNS TRIGGER AS $set_update_date$
BEGIN
    NEW.modified_date := now();
    RETURN NEW;
END
$set_update_date$ LANGUAGE plpgsql;

CREATE TABLE IF NOT EXISTS users_credentials (
    id      uuid PRIMARY KEY,
    username TEXT NOT NULL ,
    email_address TEXT NOT NULL,
    password TEXT NOT NULL,
    update_date TIMESTAMPTZ
);

CREATE TABLE IF NOT EXISTS users_info(
    id BIGINT PRIMARY KEY DEFAULT nextval('user_id_seq'),
    full_name VARCHAR,
    city VARCHAR,
    home_address TEXT,
    neighborhood VARCHAR,
    credentials_key uuid references users_credentials(id),
    created_date TIMESTAMPTZ NOT NULL DEFAULT now(),
    update_date TIMESTAMPTZ
);

CREATE TRIGGER set_update_date_users
    BEFORE UPDATE ON users_info
    FOR EACH ROW
EXECUTE PROCEDURE set_update_date();
