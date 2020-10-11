package br.com.nogueira.cooperativismo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

@Service
public class KafkaService<T> {

    @Autowired
    private KafkaTemplate<String, T> kafkaTemplate;

    public ListenableFuture<SendResult<String, T>> send(String topic, T  t) {
        return this.kafkaTemplate.send(topic, t);
    }
}