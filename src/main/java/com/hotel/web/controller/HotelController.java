package com.hotel.web.controller;

import com.hotel.entity.Hotel;
import com.hotel.mapper.HotelMapper;
import com.hotel.service.HotelService;
import com.hotel.web.dto.hotel.HotelRequest;
import com.hotel.web.dto.hotel.HotelResponse;
import com.hotel.web.dto.hotel.ListHotelResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hotels")
public class HotelController {

    private final HotelService service;
    private final HotelMapper mapper;

    @GetMapping
    public ListHotelResponse getAllHotels() {
        return mapper.hotelsListToHotelsListResponse(service.findAll());
    }

    @GetMapping("/{id}")
    public HotelResponse getHotelById(@PathVariable Long id) {
        return mapper.hotelToResponse(service.findById(id));
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public HotelResponse createHotel(@RequestBody HotelRequest hotelRequest) {
        Hotel hotel = service.save(mapper.requestToHotel(hotelRequest));
        return mapper.hotelToResponse(hotel);
    }

    @PutMapping("/{id}")
    public HotelResponse updateHotel(@PathVariable Long id, @RequestBody HotelRequest hotelRequest) {
        Hotel updated = service.update(mapper.requestToHotel(id, hotelRequest));
        return mapper.hotelToResponse(updated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteHotel(@PathVariable Long id) {
        service.delete(id);
    }
}
