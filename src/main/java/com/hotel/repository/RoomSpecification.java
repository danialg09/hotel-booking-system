package com.hotel.repository;

import com.hotel.entity.Room;
import com.hotel.web.dto.room.RoomFilter;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public interface RoomSpecification {
    static Specification<Room> withFilter(RoomFilter filter) {
        return Specification.allOf(
                byRoomId(filter.getRoomId()),
                byHotelId(filter.getHotelId()),
                byName(filter.getName()),
                byCapacity(filter.getCapacity()),
                byMinAndMaxPrice(filter.getMinPrice(), filter.getMaxPrice()),
                byCheckInAndCheckOutDates(filter.getCheckIn(), filter.getCheckOut())
        );
    }

    static Specification<Room> byMinAndMaxPrice(Long minPrice, Long maxPrice) {
        return (root, query, cb) -> {
            if (minPrice == null && maxPrice == null) {
                return null;
            }
            if (minPrice != null && maxPrice != null) {
                return cb.and(
                        cb.greaterThanOrEqualTo(root.get("price"), minPrice),
                        cb.lessThanOrEqualTo(root.get("price"), maxPrice)
                );
            } else if (minPrice != null) {
                return cb.greaterThanOrEqualTo(root.get("price"), minPrice);
            } else {
                return cb.lessThanOrEqualTo(root.get("price"), maxPrice);
            }
        };
    }

    static Specification<Room> byCheckInAndCheckOutDates(LocalDate checkIn, LocalDate checkOut) {
        return (root, query, cb) -> {
            if (checkIn == null || checkOut == null) {
                return null;
            }
            Subquery<Long> subquery = query.subquery(Long.class);
            Root<Room> subRoot = subquery.from(Room.class);
            Join<Room, LocalDate> subDates = subRoot.join("dates");
            subquery.select(subRoot.get("id"))
                    .where(cb.between(subDates, checkIn, checkOut));

            return cb.not(root.get("id").in(subquery));
        };
    }

    static Specification<Room> byName(String name) {
        return (root, query, cb) -> {
            if (name == null) {
                return null;
            }
            return cb.equal(root.get("name"), name);
        };
    }

    static Specification<Room> byCapacity(Integer capacity) {
        return (root, query, cb) -> {
            if (capacity == null) {
                return null;
            }
            return cb.equal(root.get("capacity"), capacity);
        };
    }

    static Specification<Room> byRoomId(Long roomId) {
        return (root, query, cb) -> {
            if (roomId == null) {
                return null;
            }
            return cb.equal(root.get("id"), roomId);
        };
    }

    static Specification<Room> byHotelId(Long hotelId) {
        return (root, query, cb) -> {
            if (hotelId == null) {
                return null;
            }
            System.out.println("Filtering by hotelId = " + hotelId);
            return cb.equal(root.get("hotel").get("id"), hotelId);
        };
    }
}
