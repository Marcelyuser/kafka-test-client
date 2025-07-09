package com.example.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.stereotype.Service;

import com.example.dto.KafkaConfigRequest;

@Service
public class KafkaConsumerService {

    public void startConsumer(KafkaConfigRequest request, String topic, MessageListener<String, String> listener) {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, request.getBootstrapServers());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "dynamic-group-" + System.currentTimeMillis());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        if (Boolean.parseBoolean(request.getSecurityEnabled())) {
            props.put("security.protocol", "SASL_SSL");
            props.put("sasl.mechanism", request.getSaslMechanism());
            props.put("sasl.jaas.config", request.getSaslJaasConfig());
            props.put("ssl.truststore.location", request.getSslTrustStoreLocation());
            props.put("ssl.truststore.password", request.getSslTrustStorePassword());
        }

        ContainerProperties containerProps = new ContainerProperties(topic);
        containerProps.setMessageListener(listener);

        KafkaMessageListenerContainer<String, String> container = new KafkaMessageListenerContainer<>(
                new DefaultKafkaConsumerFactory<>(props), containerProps);

        container.start();
    }
}
