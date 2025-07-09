package com.example.config;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.dto.KafkaConfigRequest;

@Service
public class KafkaProducerService {

    public String sendDynamicMessage(KafkaConfigRequest request, String topic, String message) {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, request.getBootstrapServers());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        if (Boolean.parseBoolean(request.getSecurityEnabled())) {
            props.put("security.protocol", "SASL_SSL");
            props.put("sasl.mechanism", request.getSaslMechanism());

            if (request.getSaslJaasConfig() != null) {
                props.put("sasl.jaas.config", request.getSaslJaasConfig());
            }

            if (request.getSslTrustStoreLocation() != null) {
                props.put("ssl.truststore.location", request.getSslTrustStoreLocation());
                props.put("ssl.truststore.password", request.getSslTrustStorePassword());
            }
        }

        KafkaTemplate<String, String> template = new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(props));

        try {
            RecordMetadata metadata = template.send(topic, message)
                    .get()
                    .getRecordMetadata();

            return String.format("✅ 메시지 전송 완료\nTopic: %s\nPartition: %d\nOffset: %d\nTimestamp: %d",
                    metadata.topic(), metadata.partition(), metadata.offset(), metadata.timestamp());
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            return "❌ 메시지 전송 실패: " + e.getMessage();
        }
    }
}
