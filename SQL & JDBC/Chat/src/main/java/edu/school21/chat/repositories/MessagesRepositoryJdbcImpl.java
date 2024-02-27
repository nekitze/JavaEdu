package edu.school21.chat.repositories;

import edu.school21.chat.exceptions.NotSavedSubEntityException;
import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class MessagesRepositoryJdbcImpl implements MessagesRepository {
    private DataSource dataSource;

    public MessagesRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Message> findById(Long id) {
        Message message = null;
        String statement = "SELECT " +
                "u.id AS user_id, " +
                "login, " +
                "cr.id AS room_id, " +
                "m.id AS message_id, " +
                "message_text, " +
                "date_time::timestamp(0) " +
                "FROM messages m " +
                "JOIN users u ON m.author = u.id " +
                "JOIN chat_rooms cr ON m.room = cr.id " +
                "WHERE m.id = " + id;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(statement);
             ResultSet resultSet = preparedStatement.executeQuery();) {
            if (resultSet.next()) {
                User author = new User();
                author.setId(resultSet.getLong("user_id"));
                author.setLogin(resultSet.getString("login"));

                Chatroom room = new Chatroom();
                room.setId(resultSet.getLong("room_id"));

                message = new Message();
                message.setId(resultSet.getLong("message_id"));
                message.setAuthor(author);
                message.setRoom(room);
                message.setMessageText(resultSet.getString("message_text"));
                message.setDateTime(resultSet.getString("date_time"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.ofNullable(message);
    }

    @Override
    public void save(Message message) {
        String statement = getInsertStatement(message);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(statement);
             ResultSet resultSet = preparedStatement.executeQuery();) {
            if (resultSet.next()) {
                message.setId(resultSet.getLong(1));
            }
        } catch (SQLException e) {
            throw new NotSavedSubEntityException(e);
        }
    }

    private static String getInsertStatement(Message message) {
        try {
            String author_id = message.getAuthor().getId().toString();
            String room_id = message.getRoom().getId().toString();
            String message_text = "'" + message.getMessageText() + "'";
            String date_time = "'" + message.getDateTime() + "'";

            return "INSERT INTO messages (author, room, message_text, date_time) VALUES " +
                    "(" + author_id + ", " +
                    room_id + ", " +
                    message_text + ", " +
                    date_time + ") RETURNING id";
        } catch (NullPointerException e) {
            throw new NotSavedSubEntityException("Author or room ID is null");
        }
    }

    @Override
    public void update(Message message) {
        String statement = "UPDATE messages SET (author, room, message_text, date_time) = " +
                "(" + message.getAuthor().getId() + ", " +
                message.getRoom().getId() + ", '" +
                message.getMessageText() + "', " +
                message.getDateTime() + ") " +
                "WHERE id = " + message.getId();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(statement);) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
