package com.hotel.web.dto.room;

import com.hotel.web.dto.hotel.HotelShortResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomShortResponse {
    private Long id;

    private String number;

    private Long price;

    private Integer capacity;

    private HotelShortResponse hotel;
}
