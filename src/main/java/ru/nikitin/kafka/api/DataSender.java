package ru.nikitin.kafka.api;


import ru.nikitin.kafka.model.Metric;
import ru.nikitin.kafka.model.MetricValue;
import ru.nikitin.kafka.model.Metrics;
import ru.nikitin.kafka.model.TypeMetrics;

import java.util.List;

public interface DataSender {
    void send(Metrics request);
}
