CREATE TABLE IF NOT EXISTS tracking
(
    chat_id BIGINT REFERENCES chats (id) ON DELETE CASCADE  NOT NULL,
    wallet_id BIGINT REFERENCES wallets (id) ON DELETE RESTRICT NOT NULL,
    wallet_name TEXT NOT NULL,
    PRIMARY KEY (chat_id, wallet_id)
);
