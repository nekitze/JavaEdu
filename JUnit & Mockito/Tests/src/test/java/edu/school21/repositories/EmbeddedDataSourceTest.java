package edu.school21.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

class EmbeddedDataSourceTest {
    DataSource dataSource;

    @BeforeEach
    void init() {
        dataSource = new EmbeddedDatabaseBuilder()
                .generateUniqueName(true)
                .setType(EmbeddedDatabaseType.HSQL)
                .addScripts("schema.sql", "data.sql")
                .build();
    }

    @Test
    void getConnectionTest() {
        Connection connection;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            connection = null;
        }
        Assertions.assertNotNull(connection, "Return value of getConnection() method must be a non-zero value");
    }
}
