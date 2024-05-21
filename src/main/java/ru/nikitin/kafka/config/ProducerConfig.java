package ru.nikitin.kafka.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.ssl.DefaultSslBundleRegistry;
import org.springframework.boot.ssl.SslBundles;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.JacksonUtils;
import org.springframework.kafka.support.serializer.JsonSerializer;
import ru.nikitin.kafka.api.DataSender;
import ru.nikitin.kafka.model.MetricValue;
import ru.nikitin.kafka.service.DataSenderKafka;


@Slf4j
@Configuration
public class ProducerConfig {

    public final String topicName;
//    public final String topicDltName;

    public ProducerConfig(@Value("${app.kafka.topic}") String topicName/*, @Value("${app.kafka.dlt}") String topicDltName*/) {
        this.topicName = topicName;
//        this.topicDltName = topicDltName;
    }

    @Bean
    public ObjectMapper objectMapper() {
        return JacksonUtils.enhancedObjectMapper();
    }


    @Bean
    public ProducerFactory<String, MetricValue> producerFactory(
            KafkaProperties kafkaProperties, ObjectMapper mapper) {
        SslBundles bundle = new DefaultSslBundleRegistry();
        var props = kafkaProperties.buildProducerProperties(bundle);
        props.put(org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);


        var kafkaProducerFactory = new DefaultKafkaProducerFactory<String, MetricValue>(props);
        kafkaProducerFactory.setValueSerializer(new JsonSerializer<>(mapper));
        return kafkaProducerFactory;
    }

    @Bean
    public KafkaTemplate<String, MetricValue> kafkaTemplate(
            ProducerFactory<String, MetricValue> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    public NewTopic topic() {
        return TopicBuilder.name(topicName)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public DataSender dataMetricSender(NewTopic topic, KafkaTemplate<String, MetricValue> kafkaTemplate) {
        return new DataSenderKafka(
                topic.name(),
                kafkaTemplate);
    }
//
//    @Bean
//    public NewTopic dlt() {
//        return new NewTopic(topicDltName, 1, (short) 1);
//    }
}
