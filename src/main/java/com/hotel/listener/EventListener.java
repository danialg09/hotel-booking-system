package com.hotel.listener;

import com.hotel.events.Event;
import com.hotel.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class EventListener {

    private final StatisticsService statisticsService;

    @KafkaListener(topics = "${app.kafka.statisticsTopic}",
            groupId = "${app.kafka.kafkaMessageGroupId}",
            containerFactory = "kafkaMessageConcurrentKafkaListenerContainerFactory")
    public void registrationListener(@Payload Event event) {
        log.info("Received message: {}", event);
        statisticsService.save(event);
    }
}
