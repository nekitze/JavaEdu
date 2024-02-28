# JdbcTemplate

There are two implementations of UsersRepository: UsersRepositoryJdbcImpl (uses standard Statements meachanisms) and UsersRepositoryJdbcTemplateImpl (is based on JdbcTemaplte/NamedParameterJdbcTemaple).

In context.xml file, beans declared for both repository types with different identifiers, as well as two beans of DataSource type: DriverManagerDataSource and HikariDataSource.

Data for connecting to DB specified in db.properties file and included in context.xml using ${db.url} placeholders.

Operation of findAll method demonstrated using both repositories in Main class:

```
ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
UsersRepository usersRepository = context.getBean("usersRepositoryJdbc", UsersRepository.class);
System.out.println(usersRepository.findAll());
usersRepository = context.getBean("usersRepositoryJdbcTemplate", UsersRepository.class);
System.out.println(usersRepository.findAll());
```

Configure db.properties file:
```
db.url=jdbc:postgresql://localhost:5432/postgres
db.user=postgres
db.password=1234
db.driver.name=org.postgresql.Driver
```

Run:
```
mvn package
java -jar targer/Service.jar
```
