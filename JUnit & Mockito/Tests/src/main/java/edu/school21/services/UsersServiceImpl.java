package edu.school21.services;

import edu.school21.exceptions.AlreadyAuthenticatedException;
import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;

public class UsersServiceImpl implements UsersService {
    UsersRepository usersRepository;

    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public boolean authenticate(String login, String password) {
        User user = usersRepository.findByLogin(login);

        if (user.isAuthenticated()) {
            throw new AlreadyAuthenticatedException();
        }

        if (user.getPassword().equals(password)) {
            user.setAuthenticated(true);
            usersRepository.update(user);

            return true;
        }

        return false;
    }
}
