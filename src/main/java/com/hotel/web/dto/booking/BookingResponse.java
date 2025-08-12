package com.hotel.web.dto.booking;

import com.hotel.web.dto.room.RoomResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponse {
    private Long id;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private RoomResponse room;
}
