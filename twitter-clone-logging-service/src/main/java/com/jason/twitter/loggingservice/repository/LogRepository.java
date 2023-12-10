package com.jason.twitter.loggingservice.repository;

import com.jason.twitter.loggingservice.entity.Log;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface LogRepository extends MongoRepository<Log, String> {
    List<Log> findByLevel(String level);
}
