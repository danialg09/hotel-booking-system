package com.hotel.mapper;

import com.hotel.entity.Hotel;
import com.hotel.mapper.deckorator.HotelMapperDecorator;
import com.hotel.web.dto.hotel.HotelRequest;
import com.hotel.web.dto.hotel.HotelResponse;
import com.hotel.web.dto.hotel.ListHotelResponse;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
@DecoratedWith(HotelMapperDecorator.class)
public interface HotelMapper {

    Hotel requestToHotel(HotelRequest request);

    Hotel requestToHotel(Long id, HotelRequest request);

    HotelResponse hotelToResponse(Hotel hotel);

    List<HotelResponse> hotelsListToResponseList(List<Hotel> hotels);

    default ListHotelResponse hotelsListToHotelsListResponse(List<Hotel> hotels) {
        ListHotelResponse listHotelResponse = new ListHotelResponse();
        listHotelResponse.setHotels(hotelsListToResponseList(hotels));
        return listHotelResponse;
    }
}
