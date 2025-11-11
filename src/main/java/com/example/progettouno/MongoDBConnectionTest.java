package com.example.progettouno;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.data.mongodb.core.MongoTemplate;

@Component
public class MongoDBConnectionTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @PostConstruct
    public void test() {
        long count = mongoTemplate.getCollectionNames().size();
        System.out.println("Numero di collezioni nel database: " + count);
    }
}