package com.example.kafka.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @KafkaListener(topics="${kafka.producer.topic.name}", groupId = "${kafka.consumer.group-id}")
    public void consumeMessage(String message){
        System.out.println("Consumed message:" + message);
    }
}
