package com.microservices.rating.service;

import com.microservices.rating.model.Rating;

import java.util.List;

public interface RatingService {
    Rating createRating(Rating rating);
    List<Rating> getRatings();
    List<Rating> getRatingsByUserID(String userId);
    List<Rating> getRatingsByHotelId(String hotelID);
}
