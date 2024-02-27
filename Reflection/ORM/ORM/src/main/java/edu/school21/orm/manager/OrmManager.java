package edu.school21.orm.manager;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.school21.orm.annotations.OrmColumn;
import edu.school21.orm.annotations.OrmColumnId;
import edu.school21.orm.annotations.OrmEntity;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class OrmManager {
    DataSource dataSource;
    List<Class<?>> annotatedClasses;

    public OrmManager() {
        annotatedClasses = getListOfAnnotatedClasses();
        dataSource = getDataSource();
        createTables();
    }

    public void save(Object entity) {
        String statement = generateInsertStatement(entity);
        executeUpdateStatement(statement);
    }

    public void update(Object entity) {
        String statement = generateUpdateStatement(entity);
        executeUpdateStatement(statement);
    }

    public <T> T findById(Long id, Class<T> aClass) {
        String statement = generateFindByIdStatement(id, aClass);
        ResultSet resultSet = executeStatement(statement);
        try {
            if (!resultSet.next()) return null;
            T result = aClass.newInstance();
            for (Field field : aClass.getDeclaredFields()) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(OrmColumnId.class)) {
                    field.set(result, resultSet.getLong("id"));
                } else if (field.isAnnotationPresent(OrmColumn.class)) {
                    OrmColumn ormColumn = field.getAnnotation(OrmColumn.class);
                    field.set(result, resultSet.getObject(ormColumn.name()));
                }
            }
            return result;
        } catch (SQLException | InstantiationException | IllegalAccessException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private DataSource getDataSource() {
        HikariConfig config = new HikariConfig();
        HikariDataSource dataSource;
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/postgres");
        config.setUsername("kossmali");
        config.setPassword("");
        return new HikariDataSource(config);
    }

    private List<Class<?>> getListOfAnnotatedClasses() {
        Reflections reflections = new Reflections("edu.school21.orm.models", new SubTypesScanner(false));
        return reflections.getSubTypesOf(Object.class).stream()
                .filter(c -> c.isAnnotationPresent(OrmEntity.class))
                .collect(Collectors.toList());
    }

    private void createTables() {
        for (Class<?> entity : annotatedClasses) {
            String statement = generateCreateStatement(entity);
            executeUpdateStatement(statement);
        }
    }

    private ResultSet executeStatement(String statement) {
        try {
            return dataSource.getConnection().createStatement().executeQuery(statement);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private void executeUpdateStatement(String statement) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(statement);
        ) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private String generateCreateStatement(Class<?> entity) {
        String tableName = entity.getAnnotation(OrmEntity.class).table();
        String dropTable = "DROP TABLE IF EXISTS ?;\n".replaceFirst("\\?", tableName);
        String createTable = "CREATE TABLE ? (?);".replaceFirst("\\?", tableName);

        StringBuilder sb = new StringBuilder();

        List<Field> annotatedFields = Arrays.stream(entity.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(OrmColumnId.class) ||
                        field.isAnnotationPresent(OrmColumn.class))
                .collect(Collectors.toList());

        for (Field field : annotatedFields) {
            String column = getColumnSql(field);
            sb.append(column);
        }

        String createStatement = dropTable + createTable
                .replaceFirst("\\?", sb.toString())
                .replaceFirst(",\n\\);", "\n);");

        System.out.println(createStatement);
        return createStatement;
    }

    private String getColumnSql(Field field) {
        String column;

        if (field.isAnnotationPresent(OrmColumnId.class)) {
            column = "id SERIAL PRIMARY KEY,\n";
        } else {
            column = field.getAnnotation(OrmColumn.class).name() + " ";
            column += getPostgresType(field.getType().getSimpleName());

            int maxLength = field.getAnnotation(OrmColumn.class).length();
            if (maxLength != 0) {
                column += "(" + maxLength + ")";
            }

            column += ",\n";
        }
        return column;
    }

    private String getPostgresType(String type) {
        switch (type) {
            case "String":
                return "VARCHAR";
            case "Integer":
                return "INT";
            case "Long":
                return "BIGINT";
            case "Double":
                return "DOUBLE";
            case "Boolean":
                return "BOOLEAN";
            default:
                return null;
        }
    }

    private String generateInsertStatement(Object entity) {
        String tableName = entity.getClass().getAnnotation(OrmEntity.class).table();
        String insertStatement = "INSERT INTO ? (?) VALUES (?);".replaceFirst("\\?", tableName);

        insertStatement = fillUpdateStatement(insertStatement, entity);

        Field[] fields = entity.getClass().getFields();
        for(Field field : fields) {
            if(field.isAnnotationPresent(OrmColumnId.class)) {
                try {
                    insertStatement = insertStatement.replaceFirst("\\?", (String) field.get(entity));
                } catch (IllegalAccessException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        System.out.println(insertStatement);
        return insertStatement;
    }

    private String generateUpdateStatement(Object entity) {
        String tableName = entity.getClass().getAnnotation(OrmEntity.class).table();
        String updateStatement = "UPDATE ? SET (?) = (?) WHERE id = ?;".replaceFirst("\\?", tableName);

        updateStatement = fillUpdateStatement(updateStatement, entity);

	Field[] fields = entity.getClass().getDeclaredFields();
	for(Field field : fields) {
		if(field.isAnnotationPresent(OrmColumnId.class)) {
			try {
				field.setAccessible(true);
				updateStatement = updateStatement.replaceFirst("\\?", field.get(entity).toString());
				break;
			} catch (IllegalAccessException e){
				System.out.println(e.getMessage());
			}
		}

	}

        System.out.println(updateStatement);
        return updateStatement;
    }

    private String generateFindByIdStatement(Long id, Class<?> entity) {
        String findStatement = "SELECT * FROM ? WHERE id = ?;"
                .replaceFirst("\\?", entity.getAnnotation(OrmEntity.class).table())
                .replaceFirst("\\?", id.toString());

        System.out.println(findStatement);
        return findStatement;
    }

    private String fillUpdateStatement(String statement, Object entity) {
        List<Field> annotatedFields = Arrays.stream(entity.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(OrmColumn.class))
                .collect(Collectors.toList());

        StringBuilder colsNamesBuilder = new StringBuilder();
        StringBuilder valuesBuilder = new StringBuilder();
        for (Field field : annotatedFields) {
            colsNamesBuilder.append(field.getAnnotation(OrmColumn.class).name());
            colsNamesBuilder.append(", ");

            field.setAccessible(true);
            Type fieldType = field.getType();
            try {
                if (fieldType.equals(String.class)) {
                    valuesBuilder.append("'");
                    valuesBuilder.append(field.get(entity));
                    valuesBuilder.append("'");
                } else {
                    valuesBuilder.append(field.get(entity));
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            valuesBuilder.append(", ");
        }
        colsNamesBuilder.setLength(colsNamesBuilder.length() - 2);
        valuesBuilder.setLength(valuesBuilder.length() - 2);
        statement = statement.replaceFirst("\\?", colsNamesBuilder.toString());
        statement = statement.replaceFirst("\\?", valuesBuilder.toString());

        return statement;
    }

}
