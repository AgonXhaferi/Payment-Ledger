CREATE SCHEMA IF NOT EXISTS ledger;

CREATE TABLE ledger.accounts
(
    id         UUID PRIMARY KEY,
    balance    DECIMAL(19, 4)           NOT NULL,
    currency   VARCHAR(3)               NOT NULL,
    active     BOOLEAN                  NOT NULL DEFAULT TRUE,
    version    BIGINT                   NOT NULL DEFAULT 0,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE
);

CREATE TABLE ledger.transactions
(
    id                UUID PRIMARY KEY,
    source_account_id UUID                     NOT NULL,
    target_account_id UUID                     NOT NULL,
    amount            DECIMAL(19, 4)           NOT NULL,
    reference         VARCHAR(255),
    idempotency_key   VARCHAR(255)             NOT NULL,
    status            VARCHAR(20)              NOT NULL,
    created_at        TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at        TIMESTAMP WITH TIME ZONE,

    CONSTRAINT fk_source_account FOREIGN KEY (source_account_id) REFERENCES ledger.accounts (id),
    CONSTRAINT fk_target_account FOREIGN KEY (target_account_id) REFERENCES ledger.accounts (id),
    CONSTRAINT uq_idempotency_key UNIQUE (idempotency_key)
);

CREATE INDEX idx_transactions_source ON ledger.transactions (source_account_id);
CREATE INDEX idx_transactions_target ON ledger.transactions (target_account_id);


CREATE TABLE ledger.event_publication
(
    id                     UUID                        NOT NULL,
    listener_id            VARCHAR(512)                NOT NULL,
    event_type             VARCHAR(512)                NOT NULL,
    serialized_event       VARCHAR(4000)               NOT NULL,
    publication_date       TIMESTAMP(6) WITH TIME ZONE NOT NULL,
    completion_date        TIMESTAMP(6) WITH TIME ZONE,
    completion_attempts    INTEGER                     NOT NULL DEFAULT 0,
    last_resubmission_date TIMESTAMP(6) WITH TIME ZONE,

    PRIMARY KEY (id)
);

CREATE INDEX idx_event_publication_serialized_event_hash ON ledger.event_publication (listener_id, event_type);