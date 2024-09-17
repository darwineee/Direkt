create table if not exists clients
(
    id      serial primary key not null,
    name    varchar(50)        not null,
    api_key char(36)           not null
);

create table if not exists Users
(
    id        serial primary key,
    name      varchar(50) not null,
    email     varchar(50) not null,
    client_id int references clients (id)
        on delete cascade
        on update cascade
);