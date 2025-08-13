package com.hotel.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = RoomFilterValidValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RoomFilterValid {

    String message() default "Pagination values should not be null, if you write check-in or check-out dates, both values must be not null";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
