package com.hotel.service;

import com.hotel.entity.Room;

public interface RoomService {
    Room findById(Long id);
    Room save(Room room);
    Room update(Room room);
    void delete(Long id);
}
