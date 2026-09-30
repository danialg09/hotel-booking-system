package com.hotel.events;

public record IncomingUserEvent(
        Long userId,
        String username,
        String email
) {}
