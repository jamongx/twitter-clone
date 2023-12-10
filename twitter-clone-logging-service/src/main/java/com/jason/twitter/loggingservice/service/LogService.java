package com.jason.twitter.loggingservice.service;

import com.jason.twitter.loggingservice.entity.Log;
import com.jason.twitter.loggingservice.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LogService {

    private final LogRepository logRepository;

    @Autowired
    public LogService(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    // 모든 로그 조회
    public List<Log> getAllLogs() {
        return logRepository.findAll();
    }

    // 특정 ID의 로그 조회
    public Optional<Log> getLogById(String id) {
        return logRepository.findById(id);
    }

    // 로그 생성
    public Log createLog(Log log) {
        return logRepository.save(log);
    }

    // 로그 삭제
    public void deleteLog(String id) {
        logRepository.deleteById(id);
    }

    // 필요에 따라 추가적인 로직을 서비스에 정의...
}