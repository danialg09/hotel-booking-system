package com.hotel.service;

import com.hotel.entity.Hotel;
import com.hotel.web.dto.hotel.HotelFilter;
import org.springframework.data.domain.Page;


import java.util.List;

public interface HotelService {
    List<Hotel> findAll();
    Page<Hotel> filterBy(HotelFilter filter);
    Hotel findById(Long id);
    Hotel save(Hotel hotel);
    Hotel update(Hotel hotel);
    Hotel reviewHotel(Long id, Integer rating);
    void delete(Long id);
}
