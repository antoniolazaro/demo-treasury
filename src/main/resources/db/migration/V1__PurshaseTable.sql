
drop table if exists purshase_transaction;

CREATE TABLE purshase_transaction(
    id UUID NOT NULL PRIMARY KEY,
    description VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    transaction_date TIMESTAMP NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    currency VARCHAR(3) NOT NULL
);

ALTER TABLE purshase_transaction ALTER COLUMN created_at SET DEFAULT now();
