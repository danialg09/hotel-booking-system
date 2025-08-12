package com.hotel.web.controller;

import com.hotel.entity.Booking;
import com.hotel.mapper.BookingMapper;
import com.hotel.security.AppUserPrincipal;
import com.hotel.service.BookingService;
import com.hotel.web.dto.booking.BookingRequest;
import com.hotel.web.dto.booking.BookingResponse;
import com.hotel.web.dto.booking.ListBookingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bookings")
public class BookingController {
    private final BookingService service;
    private final BookingMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public BookingResponse create(@AuthenticationPrincipal AppUserPrincipal user, @RequestBody BookingRequest request) {
        Booking booking = service.save(mapper.requestToBooking(request, user.getId()));
        return mapper.bookingToBookingResponse(booking);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ListBookingResponse findAll() {
        return mapper.bookingsToListBookingResponse(service.findAll());
    }
}
