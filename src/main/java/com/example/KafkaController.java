package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.config.KafkaConsumerService;
import com.example.config.KafkaProducerService;
import com.example.dto.KafkaConfigRequest;

@RestController
@RequestMapping("/kafka")
public class KafkaController {

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @Autowired
    private KafkaConsumerService kafkaConsumerService;

    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(
            @ModelAttribute KafkaConfigRequest config,
            @RequestParam String topic,
            @RequestParam String message) {
        String result = kafkaProducerService.sendDynamicMessage(config, topic, message);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/receive")
    public ResponseEntity<String> receiveMessages(
            @ModelAttribute KafkaConfigRequest config,
            @RequestParam String topic) {
        kafkaConsumerService.startConsumer(config, topic, record -> {
            System.out.println("Received message from topic [" + topic + "]: " + record.value());
            // 여기서 메시지를 저장하거나 WebSocket 등으로 전달 가능
        });
        return ResponseEntity.ok("Consumer started for topic " + topic);
    }
}
