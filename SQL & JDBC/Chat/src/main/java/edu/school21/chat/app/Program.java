package edu.school21.chat.app;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.school21.chat.models.User;
import edu.school21.chat.repositories.UsersRepository;
import edu.school21.chat.repositories.UsersRepositoryJdbcImpl;

import java.util.List;

public class Program {
    public static void main(String[] args) {
        HikariConfig config = new HikariConfig();
        HikariDataSource dataSource;
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/postgres");
        config.setUsername("postgres");
        config.setPassword("    ");
        dataSource = new HikariDataSource(config);

        UsersRepository usersRepository = new UsersRepositoryJdbcImpl(dataSource);
        List<User> users = usersRepository.findAll(0, 2);

        users.forEach(System.out::println);
    }
}
