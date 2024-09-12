insert into user (id, enabled) values (1, true);
insert into user (id, enabled) values (2, true);
insert into user (id, enabled) values (3, true);

insert into authority(id, name) values (1, 'ROLE_USER');
insert into authority(id, name) values (2, 'ROLE_STAFF');
insert into authority(id, name) values (3, 'ROLE_ADMIN');


insert into user_authority(user_id, authoriy_id) values (1, 1);
insert into user_authority(user_id, authoriy_id) values (2, 2);
insert into user_authority(user_id, authoriy_id) values (3, 3);

insert into account(id, user_id, username, password) values (1, 1, 'user@abc.com', '{noop}1234');
insert into account(id, user_id, username, password) values (2, 2, 'staff@abc.com', '{noop}1234');
insert into account(id, user_id, username, password) values (3, 3, 'admin@abc.com', '{noop}1234');