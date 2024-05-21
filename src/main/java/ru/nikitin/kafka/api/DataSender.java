package ru.nikitin.kafka.api;


import ru.nikitin.kafka.model.Metric;
import ru.nikitin.kafka.model.TypeMetrics;

public interface DataSender {
    void send(Metric value, TypeMetrics type);
}
