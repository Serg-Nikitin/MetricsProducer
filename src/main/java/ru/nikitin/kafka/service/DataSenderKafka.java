package ru.nikitin.kafka.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import ru.nikitin.kafka.api.DataSender;
import ru.nikitin.kafka.model.Metric;
import ru.nikitin.kafka.model.MetricValue;
import ru.nikitin.kafka.model.TypeMetrics;


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
    public void send(Metric value, TypeMetrics type) {
        try {
            log.info("value:{}", value);
            MetricValue metric = new MetricValue(type.getName(), value.key(), value.value());
            log.info("metric ={}", metric);
            template.send(topic, metric);
        } catch (Exception ex) {
            log.error("send error, value:{}", value, ex);
        }
    }
}
