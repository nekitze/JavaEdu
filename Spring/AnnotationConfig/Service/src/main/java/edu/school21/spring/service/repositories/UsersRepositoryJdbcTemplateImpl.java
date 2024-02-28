package edu.school21.spring.service.repositories;

import edu.school21.spring.service.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Component("usersRepositoryJdbcTemplate")
public class UsersRepositoryJdbcTemplateImpl implements UsersRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public UsersRepositoryJdbcTemplateImpl(@Qualifier("driverManagerDataSource") DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }


    @Override
    public Optional<User> findById(Long id) {
        String query = "SELECT * FROM users WHERE id = :id";
        return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(query, new MapSqlParameterSource()
                        .addValue("id", id),
                new BeanPropertyRowMapper<>(User.class)));
    }

    @Override
    public List<User> findAll() {
        String query = "SELECT * FROM users";
        return namedParameterJdbcTemplate.query(query, new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public void save(User entity) {
        String query = "INSERT INTO users (email, password) VALUES (:email, :password);";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(query, new MapSqlParameterSource()
                        .addValue("email", entity.getEmail())
                        .addValue("password", entity.getPassword()),
                keyHolder, new String[]{"id"});
        entity.setId(keyHolder.getKeyAs(Long.class));
    }

    @Override
    public void update(User entity) {
        String query = "UPDATE users SET email = :email, password = :password WHERE id = :id";
        namedParameterJdbcTemplate.update(query, new MapSqlParameterSource()
                .addValue("id", entity.getId())
                .addValue("email", entity.getEmail())
                .addValue("password", entity.getPassword()));
    }

    @Override
    public void delete(Long id) {
        String query = "DELETE FROM users WHERE id = :id;";
        namedParameterJdbcTemplate.update(query, new MapSqlParameterSource()
                .addValue("id", id));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        String query = "SELECT * FROM users WHERE email = :email";
        try {
            User user = namedParameterJdbcTemplate.queryForObject(query, new MapSqlParameterSource()
                            .addValue("email", email),
                    new BeanPropertyRowMapper<>(User.class));
            assert user != null;
            return Optional.of(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
