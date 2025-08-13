package com.hotel.web.dto.room;

import com.hotel.web.dto.hotel.HotelResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomResponse {
    private Long id;

    private String name;

    private String description;

    private String number;

    private Long price;

    private Integer capacity;

    private List<LocalDate> dates;

    private HotelResponse hotel;
}
