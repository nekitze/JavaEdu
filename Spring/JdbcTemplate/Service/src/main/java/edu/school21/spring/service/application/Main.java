package edu.school21.spring.service.application;

import com.zaxxer.hikari.HikariDataSource;
import edu.school21.spring.service.models.User;
import edu.school21.spring.service.repositories.UsersRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");

        createTable(context);
        UsersRepository usersRepository = context.getBean("usersRepositoryJdbc", UsersRepository.class);
        User usr1 = new User(1L, "123@mail.ru");
        User usr2 = new User(2L, "111@gmail.ru");
        User usr3 = new User(3L, "333@email.ru");
        usersRepository.save(usr1);
        usersRepository.save(usr2);
        usersRepository.save(usr3);

        System.out.println("Созданные пользователи с помощью UsersRepositoryJdbc:");
        System.out.println(usersRepository.findAll());
        System.out.println("=======================================================");
        System.out.println("Пользователь 3L удален с помощью UsersRepositoryJdbc:");
        System.out.println("Пользователь 2L изменен с помощью UsersRepositoryJdbc:");
        usersRepository.delete(3L);
        usersRepository.update(new User(2L, "no email!!!"));
        System.out.println(usersRepository.findAll());
        System.out.println("=======================================================");
        System.out.println("Находим пользователя с id = 2L с помощью UsersRepositoryJdbc:");
        System.out.println(usersRepository.findById(2L));
        System.out.println("=======================================================");
        System.out.println("Находим пользователя по email = 123@mail.ru с помощью UsersRepositoryJdbc:");
        System.out.println(usersRepository.findByEmail("123@mail.ru"));
        System.out.println("=======================================================\n\n");

        createTable(context);
        usersRepository = context.getBean("usersRepositoryJdbcTemplate", UsersRepository.class);
        usersRepository.save(usr1);
        usersRepository.save(usr2);
        usersRepository.save(usr3);

        System.out.println("Созданные пользователи с помощью UsersRepositoryJdbcTemplate:");
        System.out.println(usersRepository.findAll());
        System.out.println("=======================================================");
        System.out.println("Пользователь 3L удален с помощью UsersRepositoryJdbcTemplate:");
        System.out.println("Пользователь 2L изменен с помощью UsersRepositoryJdbcTemplate:");
        usersRepository.delete(3L);
        usersRepository.update(new User(2L, "no email!!!"));
        System.out.println(usersRepository.findAll());
        System.out.println("=======================================================");
        System.out.println("Находим пользователя с id = 2L с помощью UsersRepositoryJdbcTemplate:");
        System.out.println(usersRepository.findById(2L));
        System.out.println("=======================================================");
        System.out.println("Находим пользователя по email = 123@mail.ru с помощью UsersRepositoryJdbcTemplate:");
        System.out.println(usersRepository.findByEmail("123@mail.ru"));
        System.out.println("=======================================================");
    }

    private static void createTable(ApplicationContext context) {
        DataSource dataSource = context.getBean("hikariDataSource", HikariDataSource.class);
        try (Connection con = dataSource.getConnection();
             Statement st = con.createStatement()) {
            st.executeUpdate("DROP TABLE IF EXISTS users;");
            st.executeUpdate("CREATE TABLE users"
                    + "(id SERIAL PRIMARY KEY, email VARCHAR(50) NOT NULL);");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
