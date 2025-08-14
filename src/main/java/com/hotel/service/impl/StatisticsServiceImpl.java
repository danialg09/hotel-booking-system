package com.hotel.service.impl;

import com.hotel.entity.StatisticsDocument;
import com.hotel.events.BookingEvent;
import com.hotel.events.Event;
import com.hotel.events.UserRegistrationEvent;
import com.hotel.repository.StatisticsRepository;
import com.hotel.service.StatisticsService;
import com.opencsv.CSVWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    private final StatisticsRepository repository;

    @Override
    public StatisticsDocument save(Event event) {
        StatisticsDocument doc = new StatisticsDocument();
        doc.setTimestamp(Instant.now());
        doc.setEvent(event);
        repository.save(doc);
        return doc;
    }

    @Override
    public ByteArrayInputStream generateCsv() throws Exception {
        List<StatisticsDocument> stats = repository.findAll();
        StringWriter writer = new StringWriter();
        CSVWriter csvWriter = new CSVWriter(writer, ';',
                CSVWriter.NO_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.DEFAULT_LINE_END);

        String[] header = {"EventType", "UserId", "CheckInDate", "CheckOutDate", "Timestamp"};
        csvWriter.writeNext(header);

        for (StatisticsDocument stat : stats) {
            String[] line = getStatistics(stat);
            csvWriter.writeNext(line);
        }

        csvWriter.close();
        return new ByteArrayInputStream(writer.toString().getBytes(StandardCharsets.UTF_8));
    }

    private static String[] getStatistics(StatisticsDocument stat) {
        Object event = stat.getEvent(); // твоё поле Object
        String eventType = "";
        String userId = "";
        String checkIn = "";
        String checkOut = "";

        if (event instanceof UserRegistrationEvent ure) {
            eventType = ure.getClass().getSimpleName();
            userId = ure.getUserId();
        } else if (event instanceof BookingEvent be) {
            eventType = be.getClass().getSimpleName();
            userId = be.getUserId();
            checkIn = be.getCheckInDate().toString();
            checkOut = be.getCheckOutDate().toString();
        }

        return new String[]{eventType, userId, checkIn, checkOut, stat.getTimestamp().toString()};
    }
}
