package edu.school21.spring.service.services;

import edu.school21.spring.service.models.User;
import edu.school21.spring.service.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("usersService")
public class UsersServiceImpl implements UsersService {
    private final UsersRepository usersRepository;

    @Autowired
    public UsersServiceImpl(@Qualifier("usersRepositoryJdbc") UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public String signUp(String email) {
        if (usersRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("User with email=" + email + " already exists.");
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword("TEMPPASSWORD");

        usersRepository.save(user);
        if (user.getId() == null) {
            return null;
        }

        return user.getPassword();
    }
}
