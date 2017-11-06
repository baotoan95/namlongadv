package net.namlongadv.services;

import org.springframework.beans.factory.annotation.Autowired;

import net.namlongadv.models.User;
import net.namlongadv.repositories.UserRepository;

public class UserService {
    @Autowired
    private UserRepository userRepository;
    
    public User getUser(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }
}
