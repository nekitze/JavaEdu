package edu.school21.spring.service.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@ComponentScan("edu.school21.spring.service")
@PropertySource("classpath:db.properties")
public class ApplicationConfig {

    @Autowired
    Environment env;

    @Bean
    public DataSource driverManagerDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource(
                env.getRequiredProperty("db.url"),
                env.getRequiredProperty("db.user"),
                env.getRequiredProperty("db.password")
        );
        dataSource.setDriverClassName(env.getRequiredProperty("db.driver.name"));
        return dataSource;
    }

    @Bean
    public DataSource hikariDataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(env.getRequiredProperty("db.url"));
        hikariConfig.setUsername(env.getRequiredProperty("db.user"));
        hikariConfig.setPassword(env.getRequiredProperty("db.password"));
        hikariConfig.setDriverClassName(env.getRequiredProperty("db.driver.name"));
        return new HikariDataSource(hikariConfig);
    }
}
