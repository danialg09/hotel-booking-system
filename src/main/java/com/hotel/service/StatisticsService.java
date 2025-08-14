package com.hotel.service;

import com.hotel.entity.StatisticsDocument;
import com.hotel.events.Event;

import java.io.ByteArrayInputStream;

public interface StatisticsService {
    StatisticsDocument save(Event event);
    ByteArrayInputStream generateCsv() throws Exception;
}
