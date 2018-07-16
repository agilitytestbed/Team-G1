DROP TABLE IF EXISTS Category;
CREATE TABLE Category(
  cid INTEGER,
  name VARCHAR(256),
  PRIMARY KEY(cid)
);

DROP TABLE IF EXISTS Transaction;
CREATE TABLE Transaction(
  tid INTEGER,
  date VARCHAR(256),
  amount FLOAT,
  externalIBAN VARCHAR(256),
  type VARCHAR(256),
  cid INTEGER REFERENCES Category(cid),
  PRIMARY KEY(tid)
);