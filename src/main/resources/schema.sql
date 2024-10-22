create table if not exists accounts
(
    id           serial primary key,
    name         varchar(50)        not null,
    email        varchar(50) unique not null,
    password     varchar(255)       not null,
    enabled      boolean default false,
    customer_id  int,
    verify_token varchar(36),
    foreign key (customer_id) references accounts (id)
);

create table if not exists roles
(
    id   serial primary key,
    role varchar(10) not null
);

create table if not exists accounts_roles
(
    id         serial primary key,
    account_id int references accounts (id)
        on delete cascade,
    role_id    int references roles (id)
        on delete cascade
);