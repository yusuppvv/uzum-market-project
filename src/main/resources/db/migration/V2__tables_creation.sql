-- 1. Users table (referenced by others)
create table users (
                       visibility boolean,
                       created_at timestamp(6),
                       updated_at timestamp(6),
                       id         uuid not null primary key,
                       email      varchar(255) not null unique,
                       full_name  varchar(255) not null,
                       role       varchar(255) not null,
                       status     varchar(255) not null
);

-- 2. Category table (referenced by Product)
create table category (
                          visibility boolean,
                          created_at timestamp(6),
                          updated_at timestamp(6),
                          id         uuid not null primary key,
                          name       varchar(255) not null
);

-- 3. Product table (references users and category)
create table product (
                         price       numeric(38, 2) not null,
                         rating      real default 0.0 not null,
                         visibility  boolean,
                         created_at  timestamp(6),
                         updated_at  timestamp(6),
                         category_id uuid not null,
                         id          uuid not null primary key,
                         seller_id   uuid not null,
                         description varchar(255) not null,
                         title       varchar(255) not null
);

-- 4. Cart table (references users and product)
create table cart (
                      quantity   integer not null,
                      visibility boolean,
                      created_at timestamp(6),
                      updated_at timestamp(6),
                      id         uuid not null primary key,
                      product_id uuid not null,
                      user_id    uuid not null,
                      unique (user_id, product_id)
);

-- 5. Orders table (references users)
create table orders (
                        id          uuid not null primary key,
                        created_at  timestamp(6),
                        updated_at  timestamp(6),
                        visibility  boolean,
                        status      varchar(255) not null,
                        total_price numeric(38, 2) not null,
                        type        varchar(255) not null,
                        user_id     uuid not null
);

-- 6. Photo table (references product)
create table photo (
                       visibility boolean,
                       created_at timestamp(6),
                       updated_at timestamp(6),
                       id         uuid not null primary key,
                       product_id uuid,
                       name       varchar(255),
                       data       bytea
);

-- 7. Review table (references users and product)
create table review (
                        rating     integer not null,
                        visibility boolean,
                        created_at timestamp(6),
                        updated_at timestamp(6),
                        id         uuid not null primary key,
                        product_id uuid not null,
                        user_id    uuid not null,
                        comment    varchar(255) not null
);

-- Users constraints
alter table users
    add constraint users_role_check check (role in ('ADMIN', 'MODERATOR', 'USER', 'SELLER')),
    add constraint users_status_check check (status in ('ACTIVE', 'BLOCK'));

-- Orders constraints
alter table orders
    add constraint orders_status_check check (status in ('PAYED', 'UNPAYED')),
    add constraint orders_type_check check (type in ('CART', 'CASH', 'CLICK', 'UZUM', 'NASIYA', 'CREDIT', 'PAYME', 'PAYNET')),
    add constraint fk32ql8ubntj5uh44ph9659tiih foreign key (user_id) references users;

-- Product constraints
alter table product
    add constraint fk1mtsbur82frn64de7balymq9s foreign key (category_id) references category,
    add constraint fknuvtfgcf3ohskgoyi6v1eh1jr foreign key (seller_id) references users;

-- Cart constraints
alter table cart
    add constraint fk3d704slv66tw6x5hmbm6p2x3u foreign key (product_id) references product,
    add constraint fkg5uhi8vpsuy0lgloxk2h4w5o6 foreign key (user_id) references users,
    add constraint ukmg7gch7630wv3d0mif6gtnsy0 unique (user_id, product_id);

-- Photo constraint
alter table photo
    add constraint fk8hs00tlacip0319kutudailre foreign key (product_id) references product;

-- Review constraints
alter table review
    add constraint review_rating_check check (rating >= 1 AND rating <= 5),
    add constraint fkiyof1sindb9qiqr9o8npj8klt foreign key (product_id) references product,
    add constraint fk6cpw2nlklblpvc7hyt7ko6v3e foreign key (user_id) references users;


alter table users owner to postgres;
alter table category owner to postgres;
alter table product owner to postgres;
alter table cart owner to postgres;
alter table orders owner to postgres;
alter table photo owner to postgres;
alter table review owner to postgres;
