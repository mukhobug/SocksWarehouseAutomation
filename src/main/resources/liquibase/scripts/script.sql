-- liquibase formatted sql
-- changeset mukhobug:1
CREATE TABLE socks
(
    id          bigserial PRIMARY KEY,
    color       varchar(30) NOT NULL,
    cotton_part int         NOT NULL CHECK ( cotton_part >= 0 ) CHECK ( cotton_part <= 100 ),
    quantity    int         NOT NULL CHECK ( quantity >= 0 )
)