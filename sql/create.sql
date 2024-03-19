CREATE TABLE panier(
    uno BIGINT NOT NULL, ino INTEGER NOT NULL,
    color VARCHAR(64) DEFAULT 'null',
    pno SERIAL PRIMARY KEY
);

CREATE TABLE items(
    ino SERIAL PRIMARY KEY,
    name VARCHAR(256) NOT NULL,
    image VARCHAR(1024),
    price REAL DEFAULT 0.0
);

CREATE TABLE color(
    color VARCHAR(128),
    item INTEGER,
    FOREIGN KEY (item) REFERENCES items(ino)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
    PRIMARY KEY(color, item)
)

CREATE TABLE images(
    image VARCHAR(128),
    item INTEGER,
    FOREIGN KEY (item) REFERENCES items(ino)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
    PRIMARY KEY(image, item)
)