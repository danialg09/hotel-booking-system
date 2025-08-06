package com.hotel.web.dto.room;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomRequest {
    private Long hotelId;

    private String name;

    private String description;

    private String number;

    private Long price;

    private int capacity;
}
