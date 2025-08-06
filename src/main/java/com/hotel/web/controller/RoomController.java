package com.hotel.web.controller;

import com.hotel.entity.Room;
import com.hotel.mapper.RoomMapper;
import com.hotel.service.RoomService;
import com.hotel.web.dto.room.RoomRequest;
import com.hotel.web.dto.room.RoomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomService service;
    private final RoomMapper mapper;

    @GetMapping("/{id}")
    public RoomResponse findById(@PathVariable Long id) {
        return mapper.roomToResponse(service.findById(id));
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public RoomResponse save(@RequestBody RoomRequest roomRequest) {
        Room updated = service.save(mapper.requestToRoom(roomRequest));
        return mapper.roomToResponse(updated);
    }

    @PutMapping("/{id}")
    public RoomResponse update(@PathVariable Long id, @RequestBody RoomRequest roomRequest) {
        Room updated = service.update(mapper.requestToRoom(id, roomRequest));
        return mapper.roomToResponse(updated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
