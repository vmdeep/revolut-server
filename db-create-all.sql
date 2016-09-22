CREATE TABLE accounts (
  id        INTEGER AUTO_INCREMENT NOT NULL,
  amount    DECIMAL(38),
  curr_type INTEGER,
  deleted   BOOLEAN,
  CONSTRAINT ck_accounts_curr_type CHECK (curr_type IN (0, 1, 2)),
  CONSTRAINT pk_accounts PRIMARY KEY (id)
);

CREATE TABLE operational_book (
  id        INTEGER AUTO_INCREMENT NOT NULL,
  acc_id    INTEGER,
  amount    DECIMAL(38),
  curr_type INTEGER,
  date      TIMESTAMP,
  CONSTRAINT ck_operational_book_curr_type CHECK (curr_type IN (0, 1, 2)),
  CONSTRAINT pk_operational_book PRIMARY KEY (id)
);

CREATE TABLE rates (
  id            INTEGER AUTO_INCREMENT NOT NULL,
  currency_type INTEGER,
  rate          DECIMAL(38),
  date          TIMESTAMP,
  CONSTRAINT ck_rates_currency_type CHECK (currency_type IN (0, 1, 2)),
  CONSTRAINT pk_rates PRIMARY KEY (id)
);

