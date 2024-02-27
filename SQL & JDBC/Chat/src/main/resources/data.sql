INSERT INTO users (login, password) VALUES
    ('user', 'user'),
    ('polzovatel', 'abc123'),
    ('testuser', 'testtest'),
    ('privet_111', 'Strong_password123.'),
    ('LolKekCheburek', '123123123');

INSERT INTO chat_rooms (room_name, owner) VALUES
    ('Туса джуса', 1),
    ('Крутые бобры', 1),
    ('Backrooms', 1),
    ('Общий чат', 3),
    ('Чатик', 5);

INSERT INTO messages (author, room, message_text, date_time)  VALUES
    (1, 1, 'Всем саламалекум!', current_timestamp),
    (2, 1, 'Салам!', current_timestamp),
    (3, 4, 'Hello guys!', current_timestamp),
    (2, 4, 'Kak dela?', current_timestamp),
    (5, 5, '123', current_timestamp);