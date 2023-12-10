package com.jason.twitter.loggingservice.controller;

import com.jason.twitter.loggingservice.entity.Log;
import com.jason.twitter.loggingservice.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/logs")
public class LogController {

    private final LogRepository logRepository;

    @Autowired
    public LogController(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    // 모든 로그 조회
    @GetMapping
    public ResponseEntity<List<Log>> getAllLogs() {
        List<Log> logs = logRepository.findAll();
        return ResponseEntity.ok(logs);
    }

    // 특정 ID의 로그 조회
    @GetMapping("/{id}")
    public ResponseEntity<Log> getLogById(@PathVariable String id) {
        Optional<Log> log = logRepository.findById(id);
        if (log.isPresent()) {
            return ResponseEntity.ok(log.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 로그 생성
    @PostMapping
    public ResponseEntity<Log> createLog(@RequestBody Log log) {
        Log savedLog = logRepository.save(log);
        return ResponseEntity.ok(savedLog);
    }

    // 로그 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLog(@PathVariable String id) {
        logRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // 필요한 경우 추가적인 엔드포인트를 정의...
}
