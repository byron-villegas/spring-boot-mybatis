DROP TABLE IF EXISTS DOCUMENT;
CREATE TABLE DOCUMENT(
    ID bigint not null auto_increment primary key,
    NAME varchar(100) not null,
    FILE binary not null,
    CONTENTTYPE varchar(100) not null
);
DROP TABLE IF EXISTS PERSON;
CREATE TABLE PERSON(
    ID bigint not null auto_increment primary key,
    RUT varchar(12) not null,
    NAMES varchar(50) not null,
    SURNAMES varchar(50) not null,
    AGE tinyint not null
);