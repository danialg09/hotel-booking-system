package com.hotel.events;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookingEvent extends Event {
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
}
