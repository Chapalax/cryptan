CREATE SEQUENCE IF NOT EXISTS wallet_id_seq;

CREATE TABLE IF NOT EXISTS wallets
(
    id            BIGINT             DEFAULT nextval('wallet_id_seq') PRIMARY KEY,
    number        TEXT      NOT NULL UNIQUE,
    last_activity TIMESTAMP NOT NULL DEFAULT now(),
    checked_at    TIMESTAMP NOT NULL DEFAULT now()
);

ALTER SEQUENCE wallet_id_seq OWNED BY wallets.id;
