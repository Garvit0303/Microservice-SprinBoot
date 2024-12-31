package com.microservices.user.service.service.impl;

import com.microservices.user.service.exception.ResourceNotFoundException;
import com.microservices.user.service.model.User;
import com.microservices.user.service.repository.UserRepository;
import com.microservices.user.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User saveUser(User user) {
        String randomUserId = UUID.randomUUID().toString();
        user.setUserId(randomUserId);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(String userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with given userId is not found on the server, userId: "+userId));
    }

    @Override
    public User deleteUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with given userId is not found on the server, userId: "+userId));
        userRepository.deleteById(userId);
        return user;
    }
}
