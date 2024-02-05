DROP TABLE IF EXISTS panier;
DROP TABLE IF EXISTS items;

CREATE TABLE panier(
    uno INTEGER NOT NULL, ino INTEGER NOT NULL,
    color VARCHAR(64) DEFAULT 'null',
    pno SERIAL PRIMARY KEY
);

CREATE TABLE items(
    ino SERIAL PRIMARY KEY,
    name VARCHAR(256) NOT NULL,
    image VARCHAR(1024),
    price REAL DEFAULT 0.0
);