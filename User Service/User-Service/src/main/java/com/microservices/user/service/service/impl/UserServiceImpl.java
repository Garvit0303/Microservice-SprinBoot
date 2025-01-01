package com.microservices.user.service.service.impl;

import com.microservices.user.service.exception.ResourceNotFoundException;
import com.microservices.user.service.model.Hotel;
import com.microservices.user.service.model.Rating;
import com.microservices.user.service.model.User;
import com.microservices.user.service.repository.UserRepository;
import com.microservices.user.service.service.UserService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;


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
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with given userId is not found on the server, userId: "+userId));
        Rating[] ratingsForUser = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/"+user.getUserId(), Rating[].class);
        List<Rating> ratings = Arrays.stream(ratingsForUser).toList();
        List<Rating> ratingList = ratings.stream().map(rating -> {
             ResponseEntity<Hotel> entity =restTemplate.getForEntity("http://HOTEL-SERVICE:8082/hotels/"+rating.getHotelId(), Hotel.class);
             Hotel hotel = entity.getBody();
             rating.setHotel(hotel);
             return rating;
            }
        ).collect(Collectors.toList());
        user.setRatings(ratingList);
        return user;
    }

    @Override
    public User deleteUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with given userId is not found on the server, userId: "+userId));
        userRepository.deleteById(userId);
        return user;
    }
}
