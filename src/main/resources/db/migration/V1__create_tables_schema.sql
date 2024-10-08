CREATE TABLE Event
(
    id      INT AUTO_INCREMENT NOT NULL,
    user_id INT                NULL,
    file_id INT                NULL,
    CONSTRAINT pk_event PRIMARY KEY (id)
);

CREATE TABLE File
(
    id       INT AUTO_INCREMENT NOT NULL,
    name     VARCHAR(255)       NOT NULL,
    filePath VARCHAR(255)       NOT NULL,
    CONSTRAINT pk_file PRIMARY KEY (id)
);

CREATE TABLE User
(
    id   INT AUTO_INCREMENT NOT NULL,
    name VARCHAR(255)       NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

ALTER TABLE Event
    ADD CONSTRAINT FK_EVENT_ON_FILE FOREIGN KEY (file_id) REFERENCES File (id)
        ON DELETE SET NULL;

ALTER TABLE Event
    ADD CONSTRAINT FK_EVENT_ON_USER FOREIGN KEY (user_id) REFERENCES User (id)
        ON DELETE SET NULL;