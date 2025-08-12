package com.hotel.web.dto.room;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomRequest {

    @NotNull(message = "Cannot create a room without hotel")
    private Long hotelId;

    private String name;

    private String description;

    private String number;

    private Long price;

    private int capacity;
}
