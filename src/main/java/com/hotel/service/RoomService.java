package com.hotel.service;

import com.hotel.entity.Room;
import com.hotel.web.dto.room.RoomFilter;
import org.springframework.data.domain.Page;


public interface RoomService {
    Page<Room> filterBy(RoomFilter filter);
    Room findById(Long id);
    Room save(Room room);
    Room update(Room room);
    void delete(Long id);
}
