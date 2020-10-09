package br.com.nogueira.cooperativismo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaService<T> {

    @Autowired
    private KafkaTemplate<String, T> kafkaTemplate;

    public void send(String topic,T  t) {
        this.kafkaTemplate.send(topic, t);
    }
}