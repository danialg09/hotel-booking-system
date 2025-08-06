package com.hotel.service.impl;

import com.hotel.entity.Hotel;
import com.hotel.exception.EntityNotFoundException;
import com.hotel.repository.HotelRepository;
import com.hotel.service.HotelService;
import com.hotel.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelRepository repository;

    @Override
    public List<Hotel> findAll() {
        return repository.findAll();
    }

    @Override
    public Hotel findById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Hotel not found with id: " + id));
    }

    @Override
    public Hotel save(Hotel hotel) {
        return repository.save(hotel);
    }

    @Override
    public Hotel update(Hotel hotel) {
        Hotel exists = findById(hotel.getId());
        BeanUtils.copyNonNullProperties(hotel, exists);
        return repository.save(exists);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}

