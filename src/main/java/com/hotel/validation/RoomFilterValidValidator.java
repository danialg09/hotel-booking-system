package com.hotel.validation;

import com.hotel.web.dto.room.RoomFilter;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RoomFilterValidValidator implements ConstraintValidator<RoomFilterValid, RoomFilter> {

    @Override
    public boolean isValid(RoomFilter filter, ConstraintValidatorContext constraintValidatorContext) {
        return filter.getCheckOut() == null && filter.getCheckIn() == null ||
                filter.getCheckOut() != null && filter.getCheckIn() != null;
    }
}
