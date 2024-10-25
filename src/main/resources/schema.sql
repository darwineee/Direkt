create table if not exists accounts
(
    id           serial primary key,
    name         varchar(50)        not null,
    email        varchar(50) unique not null,
    password     varchar(255)       not null,
    enabled      boolean default false,
    customer_id  int references accounts (id),
    verify_token varchar(36)
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

create table if not exists rooms
(
    id       serial primary key,
    name     varchar(50),
    owner_id int references accounts (id)
);

create table if not exists room_members
(
    id        serial primary key,
    room_id   int references rooms (id),
    member_id int references accounts (id)
);

create table if not exists messages
(
    id         uuid primary key default gen_random_uuid(),
    room_id    int references rooms (id)
        on delete cascade,
    member_id   int references accounts (id),
    created_at timestamptz not null,
    data       text        not null
)