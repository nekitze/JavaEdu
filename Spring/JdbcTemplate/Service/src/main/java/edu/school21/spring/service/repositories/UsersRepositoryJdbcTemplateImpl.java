package edu.school21.spring.service.repositories;

import edu.school21.spring.service.models.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryJdbcTemplateImpl implements UsersRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public UsersRepositoryJdbcTemplateImpl(DataSource dataSource) {
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
        String query = "INSERT INTO users (id, email) VALUES (:id, :email)";
        namedParameterJdbcTemplate.update(query, new MapSqlParameterSource()
                .addValue("id", entity.getId())
                .addValue("email", entity.getEmail()));
    }

    @Override
    public void update(User entity) {
        String query = "UPDATE users SET email = :email WHERE id = :id";
        namedParameterJdbcTemplate.update(query, new MapSqlParameterSource()
                .addValue("id", entity.getId())
                .addValue("email", entity.getEmail()));
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
        return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(query, new MapSqlParameterSource()
                        .addValue("email", email),
                new BeanPropertyRowMapper<>(User.class)));
    }
}
