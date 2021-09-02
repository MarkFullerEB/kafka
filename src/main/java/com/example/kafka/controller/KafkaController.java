package com.example.kafka.controller;

import com.example.kafka.util.GenericResponse;
import com.example.kafka.service.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/kafka")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class KafkaController {

    @Autowired
    KafkaProducerService<String> proucer;

    @PostMapping
    public GenericResponse produce(@RequestBody String message) {
        return proucer.produce(message);
    }
}
