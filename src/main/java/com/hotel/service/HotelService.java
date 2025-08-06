package com.hotel.service;

import com.hotel.entity.Hotel;

import java.util.List;

public interface HotelService {
    List<Hotel> findAll();
    Hotel findById(Long id);
    Hotel save(Hotel hotel);
    Hotel update(Hotel hotel);
    void delete(Long id);
}
