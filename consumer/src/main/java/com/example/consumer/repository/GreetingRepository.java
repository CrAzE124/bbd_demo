package com.example.consumer.repository;

import com.example.consumer.model.GreetingModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GreetingRepository extends MongoRepository<GreetingModel, String> {
}
