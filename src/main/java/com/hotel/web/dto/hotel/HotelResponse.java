package com.hotel.web.dto.hotel;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HotelResponse {
    private Long id;
    private String name;
    private String advertisement;
    private String city;
    private String address;
    private Long distance;
    private double rating;
    private int reviews;
}
