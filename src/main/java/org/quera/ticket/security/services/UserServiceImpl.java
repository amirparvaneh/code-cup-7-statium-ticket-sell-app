package org.quera.ticket.security.services;

import org.quera.ticket.models.User;
import org.quera.ticket.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public User findUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.get();
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public List<User> findAll(){
        List<User> users = new ArrayList<>();
        return users = userRepository.findAll();
    }
}
