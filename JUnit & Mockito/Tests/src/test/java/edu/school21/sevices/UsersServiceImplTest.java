package edu.school21.sevices;

import edu.school21.exceptions.AlreadyAuthenticatedException;
import edu.school21.exceptions.EntityNotFoundException;
import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;
import edu.school21.services.UsersService;
import edu.school21.services.UsersServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UsersServiceImplTest {
    final UsersRepository usersRepository = Mockito.mock(UsersRepository.class);
    final UsersService usersService = new UsersServiceImpl(usersRepository);
    final User testUser = new User(1L, "user", "correct_password", false);

    @Test
    void correctAuthTest() {
        when(usersRepository.findByLogin("user")).thenReturn(testUser);
        usersService.authenticate("user", "correct_password");
        verify(usersRepository).update(testUser);
    }

    @Test
    void alreadyAuthenticatedTest() {
        User authenticatedUser = testUser;
        authenticatedUser.setAuthenticated(true);

        when(usersRepository.findByLogin("user")).thenReturn(authenticatedUser);

        assertThrows(AlreadyAuthenticatedException.class, () -> usersService.authenticate("user", "correct_password"));
    }

    @Test
    void incorrectLoginTest() {
        when(usersRepository.findByLogin("incorrect_login")).thenThrow(EntityNotFoundException.class);
        assertThrows(EntityNotFoundException.class, () -> usersService.authenticate("incorrect_login", "password"));
    }

    @Test
    void incorrectPasswordTest() {
        when(usersRepository.findByLogin("user")).thenReturn(testUser);
        boolean isAuthenticated = usersService.authenticate("user", "incorrect_password");
        assertFalse(isAuthenticated);
    }
}
