package com.example.consumer.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class GreetingModel {
    @Id
    private String id;
    private String greeting;

    public GreetingModel(String greeting) {
        this.greeting = greeting;
    }
}
