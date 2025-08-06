package com.hotel.service.impl;

import com.hotel.entity.Room;
import com.hotel.exception.EntityNotFoundException;
import com.hotel.repository.RoomRepository;
import com.hotel.service.RoomService;
import com.hotel.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository repository;

    @Override
    public Room findById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Room not found with id: " + id)
        );
    }

    @Override
    @Transactional
    public Room save(Room room) {
        Room saved = repository.save(room);
        saved.getHotel().addRoom(saved);
        return saved;
    }

    @Override
    @Transactional
    public Room update(Room room) {
        Room exists = findById(room.getId());
        exists.getHotel().removeRoom(exists);
        BeanUtils.copyNonNullProperties(room, exists);
        exists.getHotel().addRoom(exists);
        return repository.save(exists);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Room exists = findById(id);
        exists.getHotel().removeRoom(exists);
        repository.deleteById(id);
    }
}
