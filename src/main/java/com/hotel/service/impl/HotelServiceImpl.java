package com.hotel.service.impl;

import com.hotel.entity.Hotel;
import com.hotel.exception.EntityNotFoundException;
import com.hotel.repository.HotelRepository;
import com.hotel.specification.HotelSpecification;
import com.hotel.service.HotelService;
import com.hotel.utils.BeanUtils;
import com.hotel.web.dto.hotel.HotelFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public Page<Hotel> filterBy(HotelFilter filter) {
        return repository.findAll(
                HotelSpecification.withFilter(filter),
                PageRequest.of(filter.getPageNumber(), filter.getPageSize()));
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

