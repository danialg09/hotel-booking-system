package com.hotel.repository;

import com.hotel.entity.Hotel;
import com.hotel.web.dto.hotel.HotelFilter;
import org.springframework.data.jpa.domain.Specification;

public interface HotelSpecification {

    static Specification<Hotel> withFilter(HotelFilter filter) {
        return Specification.allOf(
                byHotelId(filter.getId()),
                byName(filter.getName()),
                byAdvertisement(filter.getAdvertisement()),
                byCity(filter.getCity()),
                byAddress(filter.getAddress()),
                byDistance(filter.getDistance()),
                byRating(filter.getRating()),
                byReviews(filter.getReviews())
        );
    }

    static Specification<Hotel> byHotelId(Long id) {
        return (root, query, cb) -> {
            if (id == null) {
                return null;
            }
            return cb.equal(root.get("id"), id);
        };
    }

    static Specification<Hotel> byName(String name) {
        return (root, query, cb) -> {
            if (name == null) {
                return null;
            }
            return cb.equal(root.get("name"), name);
        };
    }

    static Specification<Hotel> byAdvertisement(String advertisement) {
        return (root, query, cb) -> {
            if (advertisement == null) {
                return null;
            }
            return cb.equal(root.get("advertisement"), advertisement);
        };
    }

    static Specification<Hotel> byCity(String city) {
        return (root, query, cb) -> {
            if (city == null) {
                return null;
            }
            return cb.equal(root.get("city"), city);
        };
    }

    static Specification<Hotel> byAddress(String address) {
        return (root, query, cb) -> {
            if (address == null) {
                return null;
            }
            return cb.equal(root.get("address"), address);
        };
    }

    static Specification<Hotel> byDistance(Long distance) {
        return (root, query, cb) -> {
            if (distance == null) {
                return null;
            }
            return cb.equal(root.get("distance"), distance);
        };
    }

    static Specification<Hotel> byRating(Double rating) {
        return (root, query, cb) -> {
            if (rating == null) {
                return null;
            }
            return cb.equal(root.get("rating"), rating);
        };
    }

    static Specification<Hotel> byReviews(Integer reviews) {
        return (root, query, cb) -> {
            if (reviews == null) {
                return null;
            }
            return cb.equal(root.get("reviews"), reviews);
        };
    }
}
