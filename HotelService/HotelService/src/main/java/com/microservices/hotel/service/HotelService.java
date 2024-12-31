package com.microservices.hotel.service;

import com.microservices.hotel.model.Hotel;
import com.microservices.hotel.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface HotelService {

    Hotel create(Hotel hotel);

    List<Hotel> getAll();

    Hotel get(String id);
}
