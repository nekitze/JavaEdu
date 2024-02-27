package edu.school21.chat.repositories;

import edu.school21.chat.app.Program;
import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UsersRepositoryJdbcImpl implements UsersRepository {
    private DataSource dataSource;

    public UsersRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<User> findAll(int page, int size) {
        List<User> usersList;
        String statement = getQueryFromResource("findAllUsersQuery.sql");

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(statement);) {
            preparedStatement.setInt(1, page * size);
            preparedStatement.setInt(2, size);

            ResultSet resultSet = preparedStatement.executeQuery();
            usersList = getUsersDataFromResultSet(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return usersList;
    }

    private List<User> getUsersDataFromResultSet(ResultSet resultSet) throws SQLException {
        HashMap<Long, User> usersMap = new HashMap<>();
        while (resultSet.next()) {
            if (!usersMap.containsKey(resultSet.getLong("user_id"))) {
                User user = new User();

                user.setId(resultSet.getLong("user_id"));
                user.setLogin(resultSet.getString("login"));
                user.setPassword("password");

                user.setCreatedRooms(new ArrayList<>());
                user.setSocializingRooms(new ArrayList<>());

                usersMap.put(user.getId(), user);
            }
            if (resultSet.getLong("created_id") != 0) {
                Chatroom createdRoom = new Chatroom();
                createdRoom.setId(resultSet.getLong("created_id"));
                createdRoom.setName(resultSet.getString("created_room_name"));
                usersMap.get(resultSet.getLong("user_id")).addCreatedRoom(createdRoom);
            }
            if (resultSet.getLong("socializing_id") != 0) {
                Chatroom socializingRoom = new Chatroom();
                socializingRoom.setId(resultSet.getLong("socializing_id"));
                socializingRoom.setName(resultSet.getString("socializing_room_name"));
                usersMap.get(resultSet.getLong("user_id")).addSocializingRoom(socializingRoom);
            }
        }

        return new ArrayList<User>(usersMap.values());
    }

    private String getQueryFromResource(String resource_name) {
        String query;
        try (InputStream is = this.getClass()
                        .getClassLoader()
                        .getResourceAsStream(resource_name);
             BufferedReader bufferedReader =
                     new BufferedReader(new InputStreamReader(is));) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
                sb.append(' ');
            }
            query = sb.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return query;
    }
}
