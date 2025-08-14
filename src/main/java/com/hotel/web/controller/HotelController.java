package com.hotel.web.controller;

import com.hotel.entity.Hotel;
import com.hotel.mapper.HotelMapper;
import com.hotel.service.HotelService;
import com.hotel.web.dto.PageResponse;
import com.hotel.web.dto.hotel.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hotels")
public class HotelController {

    private final HotelService service;
    private final HotelMapper mapper;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ListHotelResponse getAllHotels() {
        return mapper.hotelsListToHotelsListResponse(service.findAll());
    }

    @GetMapping("/filter")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public PageResponse<HotelResponse> filterBy(@Valid @RequestBody HotelFilter filter) {
        return mapper.pageHotelToPageResponse(service.filterBy(filter));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public HotelResponse getHotelById(@PathVariable Long id) {
        return mapper.hotelToResponse(service.findById(id));
    }

    @PostMapping("/review/{id}")
    public HotelResponse reviewHotel(@PathVariable Long id, @Valid @RequestBody RatingDTO rating) {
        return mapper.hotelToResponse(service.reviewHotel(id, rating.getRating()));
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public HotelResponse createHotel(@RequestBody HotelRequest hotelRequest) {
        Hotel hotel = service.save(mapper.requestToHotel(hotelRequest));
        return mapper.hotelToResponse(hotel);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public HotelResponse updateHotel(@PathVariable Long id, @RequestBody HotelRequest hotelRequest) {
        Hotel updated = service.update(mapper.requestToHotel(id, hotelRequest));
        return mapper.hotelToResponse(updated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteHotel(@PathVariable Long id) {
        service.delete(id);
    }
}
