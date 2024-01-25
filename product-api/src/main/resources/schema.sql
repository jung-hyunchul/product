create table brand
(
    id   bigint       not null AUTO_INCREMENT,
    name varchar(100) not null,
    PRIMARY KEY (id)
);
CREATE UNIQUE INDEX idx_brand_name ON brand (name);

create table category
(
    id   bigint       not null AUTO_INCREMENT,
    name varchar(100) not null,
    PRIMARY KEY (id)
);
CREATE UNIQUE INDEX idx_category_name ON category (name);

create table product
(
    id          bigint not null AUTO_INCREMENT,
    brand_id    bigint not null,
    category_id bigint not null,
    price       bigint not null,
    name        varchar(100) default null,
    PRIMARY KEY (id)
);
CREATE UNIQUE INDEX idx_category_brand ON product (category_id, brand_id);
CREATE INDEX idx_brand_id ON product (brand_id);
CREATE INDEX idx_category_id ON product (category_id);
CREATE INDEX idx_name ON product (name);