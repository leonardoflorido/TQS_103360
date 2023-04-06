CREATE TABLE music
(
    id           BIGSERIAL PRIMARY KEY,
    name         varchar(255) not null unique,
    band         varchar(255) not null,
    release_year int          not null
);