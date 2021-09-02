package com.example.kafka.service;

import com.example.kafka.util.GenericResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class KafkaProducerService<T> {

    private final Logger log = LoggerFactory.getLogger(KafkaProducerService.class);

    @Value("${kafka.producer.topic.name}")
    private String topicName;

    @Autowired
    private KafkaTemplate<String, T> kafkaTemplate;

    public GenericResponse produce(T payrollInfo) {
        log.info("Sending message:" + payrollInfo.toString());
        ListenableFuture<SendResult<String, T>> future = kafkaTemplate.send(topicName, payrollInfo);
        GenericResponse genericResponse = GenericResponse.createSuccessResponse();
        future.addCallback(
                new ListenableFutureCallback<SendResult<String, T>>() {

                    @Override
                    public void onSuccess(SendResult<String, T> result) {
                        log.info(
                                "Sent message to [{}], with content =[{}] with offset=[{}]", topicName, payrollInfo, result.getRecordMetadata().offset());
                    }

                    @Override
                    public void onFailure(Throwable ex) {
                        genericResponse.failResponse();
                        genericResponse.addMessage(String.format("Unable to send payroll info: %s", payrollInfo));
                        log.info("Unable to send message=[{}] due to : {}", payrollInfo, ex.getMessage());
                    }
                });
        return genericResponse;
    }
}