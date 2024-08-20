explain
select distinct u.id,
                u.enabled,
                u.password,
                ua.user_id,
                ua.id,
                ua.authoriy_id,
                a.id,
                a.name,
                u.username
from user u
         join user_authority ua on u.id = ua.user_id
         join authority a on a.id = ua.authoriy_id
where u.username = 'admin';