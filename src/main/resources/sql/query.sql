explain
select distinct ac.id,
                ac.user_id,
                ac.username,
                ac.password,
                u.id,
                u.enabled,
                ua.id,
                ua.user_id,
                ua.authoriy_id,
                at.id,
                at.name
from account ac
         join user u on u.id = ac.user_id
         join user_authority ua on u.id = ua.user_id
         join authority at on ac.id = ua.authoriy_id
where ac.username = 'admin';