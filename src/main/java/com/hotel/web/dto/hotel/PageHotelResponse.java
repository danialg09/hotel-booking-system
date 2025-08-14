package com.hotel.web.dto.hotel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageHotelResponse {
    private List<HotelResponse> hotels;
    private Long totalPages;
}
