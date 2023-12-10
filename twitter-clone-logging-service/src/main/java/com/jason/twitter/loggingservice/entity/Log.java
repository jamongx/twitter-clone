package com.jason.twitter.loggingservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "logs")
public class Log {
    @Id
    private String id;

    private LocalDateTime timestamp;
    private String level;
    private String message;
    private String serviceName;
    private String exceptionDetails;

}