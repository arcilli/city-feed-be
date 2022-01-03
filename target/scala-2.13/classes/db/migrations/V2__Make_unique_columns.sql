ALTER TABLE users_credentials ADD UNIQUE (username);
ALTER TABLE users_credentials ADD UNIQUE (email_address);
