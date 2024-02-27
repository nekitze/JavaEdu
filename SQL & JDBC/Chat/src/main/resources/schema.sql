CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    login text NOT NULL,
    password text NOT NULL
);

CREATE TABLE chat_rooms (
    id SERIAL PRIMARY KEY,
    room_name text NOT NULL,
    owner bigint NOT NULL,
        CONSTRAINT fk_chat_rooms_owner_users_id FOREIGN KEY (owner) REFERENCES  users(id)
);

CREATE TABLE messages (
    id SERIAL PRIMARY KEY,
    author bigint NOT NULL,
    room bigint NOT NULL,
    message_text text,
    date_time timestamp,
        CONSTRAINT fk_messages_author_users_id FOREIGN KEY (author) REFERENCES users(id),
        CONSTRAINT fk_messages_chat_rooms_users_id FOREIGN KEY (room) REFERENCES chat_rooms(id)
);