package com.hotel.mapper;

import com.hotel.entity.Booking;
import com.hotel.mapper.deckorator.BookingMapperDecorator;
import com.hotel.web.dto.booking.BookingRequest;
import com.hotel.web.dto.booking.BookingResponse;
import com.hotel.web.dto.booking.ListBookingResponse;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {
            RoomMapper.class, HotelMapper.class
})
@DecoratedWith(BookingMapperDecorator.class)
public interface BookingMapper {

    Booking requestToBooking(BookingRequest request, Long userId);

    Booking requestToBooking(Long id, BookingRequest request, Long userId);

    BookingResponse bookingToBookingResponse(Booking booking);

    List<BookingResponse> bookingsToBookingResponses(List<Booking> bookings);

    default ListBookingResponse bookingsToListBookingResponse(List<Booking> bookings) {
        ListBookingResponse listBookingResponse = new ListBookingResponse();
        listBookingResponse.setBookings(bookingsToBookingResponses(bookings));
        return listBookingResponse;
    }
}
