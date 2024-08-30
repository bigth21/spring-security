drop table if exists authority;
drop table if exists user;
drop table if exists user_authority;
drop table if exists account;

create table authority
(
    id   bigint not null auto_increment,
    name varchar(20),
    primary key (id)
) engine = InnoDB;

create table user
(
    id      bigint not null auto_increment,
    enabled bit    not null,
    primary key (id)
) engine = InnoDB;

create table user_authority
(
    id          bigint not null auto_increment,
    user_id     bigint not null,
    authoriy_id bigint not null,
    primary key (id)
) engine = InnoDB;
alter table user_authority
    add constraint ix_userid_authoriyid unique (user_id, authoriy_id);

create table account
(
    id       bigint       not null auto_increment,
    user_id  bigint       not null,
    username varchar(255) not null,
    password varchar(255) not null,
    primary key (id)
) engine = InnoDB;
create index ix_userid on account(user_id);
alter table account
    add constraint ix_username unique (username);