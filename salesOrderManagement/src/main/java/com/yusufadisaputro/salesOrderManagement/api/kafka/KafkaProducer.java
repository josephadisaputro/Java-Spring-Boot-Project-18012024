package com.yusufadisaputro.salesOrderManagement.api.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("kafka")
public class KafkaProducer {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @GetMapping("/publish")
    public String post() {
        kafkaTemplate.send("MyTopic", "Hello, World!");
        return "Published successfully";
    }
}
