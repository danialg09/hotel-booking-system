package com.hotel.mapper.deckorator;

import com.hotel.entity.Booking;
import com.hotel.mapper.BookingMapper;
import com.hotel.service.RoomService;
import com.hotel.service.UserService;
import com.hotel.web.dto.booking.BookingRequest;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BookingMapperDecorator implements BookingMapper {

    @Autowired
    private UserService userService;

    @Autowired
    private RoomService roomService;

    @Override
    public Booking requestToBooking(BookingRequest request, Long userId) {
        Booking booking = new Booking();
        booking.setUser(userService.findById(userId));
        booking.setRoom(roomService.findById(request.getRoomId()));
        booking.setCheckIn(request.getCheckIn());
        booking.setCheckOut(request.getCheckOut());
        return booking;
    }

    @Override
    public Booking requestToBooking(Long id, BookingRequest request, Long userId) {
        Booking booking = requestToBooking(request, userId);
        booking.setId(id);
        return booking;
    }
}
