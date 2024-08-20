drop table if exists authority;
drop table if exists user;
drop table if exists user_authority;

create table authority
(
    id   bigint not null auto_increment,
    name varchar(20),
    primary key (id)
) engine = InnoDB;

create table user
(
    id       bigint       not null auto_increment,
    username varchar(255) not null,
    password varchar(255) not null,
    enabled  bit          not null,
    primary key (id)
) engine = InnoDB;
alter table user
    add constraint ix_username unique (username);

create table user_authority
(
    id          bigint not null auto_increment,
    user_id     bigint not null,
    authoriy_id bigint not null,
    primary key (id)
) engine = InnoDB