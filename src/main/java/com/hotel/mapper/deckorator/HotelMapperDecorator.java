package com.hotel.mapper.deckorator;

import com.hotel.entity.Hotel;
import com.hotel.mapper.HotelMapper;
import com.hotel.web.dto.hotel.HotelRequest;
import org.springframework.stereotype.Component;

@Component
public abstract class HotelMapperDecorator implements HotelMapper {

    @Override
    public Hotel requestToHotel(HotelRequest request) {
        Hotel hotel = new Hotel();
        hotel.setName(request.getName());
        hotel.setAdvertisement(request.getAdvertisement());
        hotel.setCity(request.getCity());
        hotel.setAddress(request.getAddress());
        hotel.setDistance(request.getDistance());
        hotel.setRating(0);
        hotel.setReviews(0);
        return hotel;
    }

    @Override
    public Hotel requestToHotel(Long id, HotelRequest request) {
        Hotel hotel = requestToHotel(request);
        hotel.setId(id);
        return hotel;
    }
}
