package ru.nikitin.kafka.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import ru.nikitin.kafka.api.DataSender;
import ru.nikitin.kafka.model.MetricValue;
import ru.nikitin.kafka.model.Metrics;


@Slf4j
public class DataSenderKafka implements DataSender {

    private final KafkaTemplate<String, MetricValue> template;

    private final String topic;

    public DataSenderKafka(
            String topic,
            KafkaTemplate<String, MetricValue> template) {
        this.topic = topic;
        this.template = template;
    }

    @Override
    public void send(Metrics request) {
        try {
            for (MetricValue value : request.metrics()) {
                log.info("metric ={}", value);
                template.send(topic, value);
            }
        } catch (Exception ex) {
            log.error("send error, value:{}", request, ex);
        }
    }
}
