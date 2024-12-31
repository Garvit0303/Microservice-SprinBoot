package com.microservices.user.service.service;

import com.microservices.user.service.model.User;

import java.util.List;

public interface UserService {

    User saveUser(User user);

    List<User> getAllUsers();

    User getUser(String userId);

    User deleteUser(String userId);
}
