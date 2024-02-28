# AnnotationConfig

Project Features:
- Spring application configured using annotations
- Implemented UsersService/UsersServiceImpl interface/class pair with a dependency on UsersRepository declared in it.
- Implemented signUp(String email)method that registers a new user and saves its details in DB.
- Implemented an integration test for UsersServiceImp using an in-memory database (H2 or HSQLDB).
- The context configuration for test environment (DataSource for in-memory database) described in a separate TestApplicatoinConfig class

**Project structure**:
- Service
    - src
        - main
            - java
                - school21.spring.service
                    - models
                        - User
                    - repositories
                        - CrudRepository
                        - UsersRepository
                        - UsersRepositoryJdbcImpl
                        - UsersRepositoryJdbcTemplateImpl
                    - application
                        - Main
            - resources
                -	db.properties
                -	context.xml
    -	pom.xml
