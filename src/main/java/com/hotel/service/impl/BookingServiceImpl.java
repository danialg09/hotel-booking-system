package com.hotel.service.impl;

import com.hotel.entity.Booking;
import com.hotel.exception.BookingAlreadyExistsException;
import com.hotel.exception.EntityNotFoundException;
import com.hotel.repository.BookingRepository;
import com.hotel.service.BookingService;
import com.hotel.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository repository;

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
        LocalDate in = booking.getCheckIn();
        LocalDate out = booking.getCheckOut();
        List<Booking> conflicts = repository.findOverlappingBookings(roomId, in, out);
        if (!conflicts.isEmpty()) {
            StringBuilder message = new StringBuilder("Room already booked for the following periods: ");
            for (Booking b : conflicts) {
                message.append("[").append(b.getCheckIn()).append(" - ").append(b.getCheckOut()).append("], ");
            }
            message.setLength(message.length() - 2);
            throw new BookingAlreadyExistsException(message.toString());
        }
    }
}
