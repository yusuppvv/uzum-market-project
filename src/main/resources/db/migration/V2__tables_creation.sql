-- Cart table
create table cart
(
    quantity   integer not null,
    visibility boolean,
    created_at timestamp(6),
    updated_at timestamp(6),
    id         uuid    not null
        primary key,
    product_id uuid    not null
        constraint fk3d704slv66tw6x5hmbm6p2x3u
            references product,
    user_id    uuid    not null
        constraint fkg5uhi8vpsuy0lgloxk2h4w5o6
            references users,
    unique (user_id, product_id),
    constraint ukmg7gch7630wv3d0mif6gtnsy0
        unique (user_id, product_id)
);

alter table cart
    owner to postgres

-- Category table
create table category
(
    visibility boolean,
    created_at timestamp(6),
    updated_at timestamp(6),
    id         uuid         not null
        primary key,
    name       varchar(255) not null
);

alter table category
    owner to postgres;

-- Orders table
create table orders
(
    id          uuid           not null
        primary key,
    created_at  timestamp(6),
    updated_at  timestamp(6),
    visibility  boolean,
    status      varchar(255)   not null
        constraint orders_status_check
            check ((status)::text = ANY ((ARRAY ['PAYED'::character varying, 'UNPAYED'::character varying])::text[])),
    total_price numeric(38, 2) not null,
    type        varchar(255)   not null
        constraint orders_type_check
            check ((type)::text = ANY
                   ((ARRAY ['CART'::character varying, 'CASH'::character varying, 'CLICK'::character varying, 'UZUM'::character varying, 'NASIYA'::character varying, 'CREDIT'::character varying, 'PAYME'::character varying, 'PAYNET'::character varying])::text[])),
    user_id     uuid           not null
        constraint fk32ql8ubntj5uh44ph9659tiih
            references users
);

alter table orders
    owner to postgres;

-- Photo table
create table photo
(
    visibility boolean,
    created_at timestamp(6),
    updated_at timestamp(6),
    id         uuid not null
        primary key,
    product_id uuid
        constraint fk8hs00tlacip0319kutudailre
            references product,
    name       varchar(255),
    data       bytea
);

alter table photo
    owner to postgres;

-- Product table
create table product
(
    price       numeric(38, 2)   not null,
    rating      real default 0.0 not null,
    visibility  boolean,
    created_at  timestamp(6),
    updated_at  timestamp(6),
    category_id uuid             not null
        constraint fk1mtsbur82frn64de7balymq9s
            references category,
    id          uuid             not null
        primary key,
    seller_id   uuid             not null
        constraint fknuvtfgcf3ohskgoyi6v1eh1jr
            references users,
    description varchar(255)     not null,
    title       varchar(255)     not null
);

alter table product
    owner to postgres;

-- Review table
create table review
(
    rating     integer      not null
        constraint review_rating_check
            check ((rating >= 1) AND (rating <= 5)),
    visibility boolean,
    created_at timestamp(6),
    updated_at timestamp(6),
    id         uuid         not null
        primary key,
    product_id uuid         not null
        constraint fkiyof1sindb9qiqr9o8npj8klt
            references product,
    user_id    uuid         not null
        constraint fk6cpw2nlklblpvc7hyt7ko6v3e
            references users,
    comment    varchar(255) not null
);

alter table review
    owner to postgres;

-- Users table
create table users
(
    visibility boolean,
    created_at timestamp(6),
    updated_at timestamp(6),
    id         uuid         not null
        primary key,
    email      varchar(255) not null
        unique,
    full_name  varchar(255) not null,
    role       varchar(255) not null
        constraint users_role_check
            check ((role)::text = ANY
        ((ARRAY ['ADMIN'::character varying, 'MODERATOR'::character varying, 'USER'::character varying, 'SELLER'::character varying])::text[])),
    status     varchar(255) not null
        constraint users_status_check
            check ((status)::text = ANY ((ARRAY ['ACTIVE'::character varying, 'BLOCK'::character varying])::text[]))
);

alter table users
    owner to postgres;

