package com.hotel.mapper;

import com.hotel.entity.Room;
import com.hotel.mapper.deckorator.RoomMapperDecorator;
import com.hotel.web.dto.PageResponse;
import com.hotel.web.dto.room.*;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {
        HotelMapper.class
})
@DecoratedWith(RoomMapperDecorator.class)
public interface RoomMapper {
    Room requestToRoom(RoomRequest request);

    Room requestToRoom(Long id, RoomRequest request);

    RoomShortResponse roomToShortResponse(RoomRequest request);

    RoomResponse roomToResponse(Room room);

    PageResponse<RoomResponse> pageRoomToPageResponse(Page<Room> room);

    List<RoomResponse> roomsListToResponseList(List<Room> rooms);
}
