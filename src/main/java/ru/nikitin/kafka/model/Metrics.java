package ru.nikitin.kafka.model;

import java.util.List;

public record Metrics(List<MetricValue> metrics) {
}
