package com.hotel.web.dto.room;

import com.hotel.validation.RoomFilterValid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@RoomFilterValid
public class RoomFilter {

    @Builder.Default
    private Integer pageNumber = 0;

    @Builder.Default
    private Integer pageSize = 10;

    private Long roomId;
    private String name;
    private Long minPrice;
    private Long maxPrice;
    private Integer capacity;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private Long hotelId;
}
