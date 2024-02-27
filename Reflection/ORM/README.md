# ORM

Implementation a trivial version of ORM framework.

Let's assume we have a set of model classes. Each class contains no dependencies on other classes, and its fields may only accept the following value types: String, Integer, Double, Boolean, Long. Let's specify a certain set of annotations for the class and its members, for example, User class:
``` Java
@OrmEntity(table = “simple_user”)
public class User {
  @OrmColumnId
  private Long id;
  @OrmColumn(name = “first_name”, length = 10)
  private String firstName;
  @OrmColumn(name = “first_name”, length = 10)
  private String lastName;
  @OrmColumn(name “age”)
  private Integer age;
  
  // setters/getters
}
```
OrmManager class generate and execute respective SQL code during initialization of all classes marked with @OrmEntity annotation. That code contain CREATE TABLE command for creating a table with the name specified in the annotation. Each field of the class marked with @OrmColumn annotation becomes a column in this table. The field marked with @OrmColumnId annotation indicates that an auto increment identifier must be created. OrmManager also support the following set of operations (the respective SQL code in Runtime is also generated for each of them):
``` Java
public void save(Object entity);

public void update(Object entity);

public <T> T findById(Long id, Class<T> aClass);
```
