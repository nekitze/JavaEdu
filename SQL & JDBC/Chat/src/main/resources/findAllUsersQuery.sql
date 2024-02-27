WITH users_page AS (
    SELECT u.id AS user_id, login, password FROM users u
    OFFSET ?
    LIMIT ?
),
created_rooms AS (
    SELECT cr.id as created_id, room_name, owner FROM chat_rooms cr
    RIGHT JOIN users_page ON cr.owner = users_page.user_id
),
socializing_rooms AS (
    SELECT DISTINCT cr.id AS socializing_id, room_name, owner, up.user_id FROM chat_rooms cr
    JOIN messages m ON cr.id = m.room
    JOIN users_page up ON up.user_id = m.author
    WHERE m.author IN (SELECT user_id FROM users_page)
)
SELECT up.user_id, up.login, up.password, cr.created_id, cr.room_name AS created_room_name,
    cr.owner AS created_room_owner, NULL AS socializing_id, NULL AS socializing_room_name
FROM users_page up LEFT JOIN created_rooms cr ON up.user_id = cr.owner
UNION
SELECT up.user_id, up.login, up.password, NULL AS created_id, NULL AS created_room_name,
    NULL AS created_room_owner, sr.socializing_id, sr.room_name AS socializing_room_name
FROM users_page up LEFT JOIN socializing_rooms sr ON up.user_id = sr.user_id;