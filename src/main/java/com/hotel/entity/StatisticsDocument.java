package com.hotel.entity;

import com.hotel.events.Event;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "statistics")
public class StatisticsDocument {

    @Id
    private String id;

    private Instant timestamp;
    private Event event;
}
