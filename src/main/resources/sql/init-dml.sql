insert into user (id, username, password, enabled) values (1, 'user', '{noop}1234', true);
insert into user (id, username, password, enabled) values (2, 'admin', '{noop}1234', true);

insert into authority(id, name) values (1, 'ROLE_USER');
insert into authority(id, name) values (2, 'ROLE_ADMIN');

insert into user_authority(user_id, authoriy_id) values (1, 1);
insert into user_authority(user_id, authoriy_id) values (2, 1);
insert into user_authority(user_id, authoriy_id) values (2, 2);