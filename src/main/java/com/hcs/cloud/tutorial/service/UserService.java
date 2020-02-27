package com.hcs.cloud.tutorial.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcs.cloud.tutorial.exception.UserNotFoundException;
import com.hcs.cloud.tutorial.model.User;
import com.hcs.cloud.tutorial.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> retrieveUsers() {
        List<User> users = this.userRepository.findAll();
        return users;
    }

    public User retrieveUser(Long id) {
        Optional<User> user = this.userRepository.findById(id);
        if (!user.isPresent()) {
            throw new UserNotFoundException("User Not Found");
        }
        return user.get();
    }

    public User saveUser(User user) {
        User savedUser = this.userRepository.save(user);
        return savedUser;
    }

    public void deleteUser(Long id) {
        Optional<User> user = this.userRepository.findById(id);
        if (!user.isPresent()) {
            throw new UserNotFoundException("User Not Found");
        }
        this.userRepository.deleteById(id);
    }
}
