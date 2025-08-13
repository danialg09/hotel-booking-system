package com.hotel.service.impl;

import com.hotel.entity.Room;
import com.hotel.exception.EntityNotFoundException;
import com.hotel.repository.RoomRepository;
import com.hotel.repository.RoomSpecification;
import com.hotel.service.RoomService;
import com.hotel.utils.BeanUtils;
import com.hotel.web.dto.room.RoomFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository repository;

    @Override
    public List<Room> filterBy(RoomFilter filter) {
        return repository.findAll(RoomSpecification.withFilter(filter),
                PageRequest.of(filter.getPageNumber(), filter.getPageSize())).getContent();
    }

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
