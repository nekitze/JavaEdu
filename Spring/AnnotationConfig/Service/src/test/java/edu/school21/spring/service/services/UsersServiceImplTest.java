package edu.school21.spring.service.services;

import edu.school21.spring.service.config.TestApplicationConfig;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Import;

import java.lang.reflect.Executable;

import static org.junit.jupiter.api.Assertions.*;

@Import({TestApplicationConfig.class})
class UsersServiceImplTest {
    static ApplicationContext context;
    static UsersService usersService;

    @BeforeAll
    static void init() {
        context = new AnnotationConfigApplicationContext(TestApplicationConfig.class);
        usersService = context.getBean("usersService", UsersService.class);
    }

    @Test
    void signUp() {
        String tempPassword = usersService.signUp("kossmali");

        assertNotNull(tempPassword);
        assertEquals(tempPassword, "TEMPPASSWORD");
    }

    @Test
    void signUpExisting() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            usersService.signUp("kossmali");
        });

        assertEquals("User with email=kossmali already exists.", exception.getMessage());
    }
}