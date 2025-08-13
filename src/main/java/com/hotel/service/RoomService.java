package com.hotel.service;

import com.hotel.entity.Room;
import com.hotel.web.dto.room.RoomFilter;

import java.util.List;

public interface RoomService {
    List<Room> filterBy(RoomFilter filter);
    Room findById(Long id);
    Room save(Room room);
    Room update(Room room);
    void delete(Long id);
}
