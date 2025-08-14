package com.hotel.mapper.deckorator;

import com.hotel.entity.Hotel;
import com.hotel.entity.Room;
import com.hotel.mapper.RoomMapper;
import com.hotel.service.HotelService;
import com.hotel.web.dto.PageResponse;
import com.hotel.web.dto.room.RoomRequest;
import com.hotel.web.dto.room.RoomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

public abstract class RoomMapperDecorator implements RoomMapper {

    @Autowired
    HotelService hotelService;

    @Override
    public Room requestToRoom(RoomRequest request) {
        Hotel hotel = hotelService.findById(request.getHotelId());
        Room room = new Room();
        room.setHotel(hotel);
        room.setName(request.getName());
        room.setCapacity(request.getCapacity());
        room.setDescription(request.getDescription());
        room.setNumber(request.getNumber());
        room.setPrice(request.getPrice());
        return room;
    }

    @Override
    public Room requestToRoom(Long id, RoomRequest request) {
        Room room = requestToRoom(request);
        room.setId(id);
        return room;
    }

    @Override
    public PageResponse<RoomResponse> pageRoomToPageResponse(Page<Room> room) {
        return new PageResponse<>(
                roomsListToResponseList(room.getContent()), room.getTotalElements());
    }
}
