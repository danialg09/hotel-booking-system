package com.hotel.web.dto.hotel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HotelFilter {

    @Builder.Default
    private Integer pageNumber = 0;

    @Builder.Default
    private Integer pageSize = 10;

    private Long id;
    private String name;
    private String advertisement;
    private String city;
    private String address;
    private Long distance;
    private Double rating;
    private Integer reviews;
}
