package com.hotel.mapper;

import com.hotel.entity.Room;
import com.hotel.mapper.deckorator.RoomMapperDecorator;
import com.hotel.web.dto.room.RoomRequest;
import com.hotel.web.dto.room.RoomResponse;
import com.hotel.web.dto.room.RoomShortResponse;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
@DecoratedWith(RoomMapperDecorator.class)
public interface RoomMapper {
    Room requestToRoom(RoomRequest request);

    Room requestToRoom(Long id, RoomRequest request);

    RoomShortResponse roomToShortResponse(RoomRequest request);

    RoomResponse roomToResponse(Room room);
}
