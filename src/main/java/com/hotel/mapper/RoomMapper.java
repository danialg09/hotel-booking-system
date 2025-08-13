package com.hotel.mapper;

import com.hotel.entity.Room;
import com.hotel.mapper.deckorator.RoomMapperDecorator;
import com.hotel.web.dto.room.ListRoomResponse;
import com.hotel.web.dto.room.RoomRequest;
import com.hotel.web.dto.room.RoomResponse;
import com.hotel.web.dto.room.RoomShortResponse;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

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

    List<RoomResponse> roomsToResponses(List<Room> rooms);

    default ListRoomResponse roomsToListResponse(List<Room> rooms) {
        ListRoomResponse listRoomResponse = new ListRoomResponse();
        listRoomResponse.setRooms(roomsToResponses(rooms));
        return listRoomResponse;
    }
}
