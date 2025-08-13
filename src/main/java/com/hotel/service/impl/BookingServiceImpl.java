package com.hotel.service.impl;

import com.hotel.entity.Booking;
import com.hotel.entity.Room;
import com.hotel.exception.BookingAlreadyExistsException;
import com.hotel.exception.EntityNotFoundException;
import com.hotel.repository.BookingRepository;
import com.hotel.repository.RoomRepository;
import com.hotel.service.BookingService;
import com.hotel.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository repository;
    private final RoomRepository roomRepository;

    @Override
    public List<Booking> findAll() {
        return repository.findAll();
    }

    @Override
    public Booking findById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Booking not found with id: " + id)
        );
    }

    @Override
    public Booking save(Booking booking) {
        checkBooking(booking);
        return repository.save(booking);
    }

    @Override
    @Transactional
    public Booking update(Booking booking) {
        checkBooking(booking);
        Booking exists = findById(booking.getId());
        BeanUtils.copyNonNullProperties(booking, exists);
        return repository.save(exists);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    public void checkBooking(Booking booking) {
        Long roomId = booking.getRoom().getId();
        Room room = roomRepository.findById(roomId).orElseThrow(
                () -> new EntityNotFoundException("Room not found with id: " + roomId)
        );
        LocalDate in = booking.getCheckIn();
        LocalDate out = booking.getCheckOut();
        if (!out.isAfter(in)) {
            throw new IllegalArgumentException("Check-out date must be after check-in date");
        }
        List<Booking> conflicts = repository.findOverlappingBookings(roomId, in, out);
        if (!conflicts.isEmpty()) {
            String periods = conflicts.stream()
                    .map(b -> "[" + b.getCheckIn() + " - " + b.getCheckOut() + "]")
                    .collect(Collectors.joining(", "));
            throw new BookingAlreadyExistsException("Room already booked for the following periods: " + periods);
        }
        room.blockDates(in, out);
        roomRepository.save(room);
    }
}
