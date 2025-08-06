package com.hotel.web.dto.hotel;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ListHotelResponse {
    private List<HotelResponse> hotels;
}
