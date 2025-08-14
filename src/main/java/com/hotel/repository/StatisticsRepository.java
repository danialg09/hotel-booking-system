package com.hotel.repository;

import com.hotel.entity.StatisticsDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StatisticsRepository extends MongoRepository<StatisticsDocument, String> {
}
