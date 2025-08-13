package com.hotel.service.impl;

import com.hotel.entity.Hotel;
import com.hotel.exception.EntityNotFoundException;
import com.hotel.repository.HotelRepository;
import com.hotel.service.HotelService;
import com.hotel.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelRepository repository;

    @Override
    @Transactional
    public Hotel reviewHotel(Long id, Integer rating) {
        Hotel hotel = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Hotel not found with id: " + id));

        double currentRating = hotel.getRating();
        int reviewsCount = hotel.getReviews();

        if (reviewsCount == 0) currentRating = rating;
        else {
            double totalRating = currentRating * reviewsCount;
            totalRating = totalRating - currentRating + rating;
            currentRating = Math.round((totalRating / reviewsCount) * 10.0) / 10.0;
        }

        hotel.setRating(currentRating);
        hotel.setReviews(reviewsCount + 1);
        return repository.save(hotel);
    }

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
    @Transactional
    public Hotel save(Hotel hotel) {
        return repository.save(hotel);
    }

    @Override
    @Transactional
    public Hotel update(Hotel hotel) {
        Hotel exists = findById(hotel.getId());
        BeanUtils.copyNonNullProperties(hotel, exists);
        return repository.save(exists);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
}

