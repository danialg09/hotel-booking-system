package com.hotel.service;

import com.hotel.entity.Booking;

import java.util.List;

public interface BookingService {
    List<Booking> findAll();
    Booking findById(Long id);
    Booking save(Booking booking);
    Booking update(Booking booking);
    void delete(Long id);
}
