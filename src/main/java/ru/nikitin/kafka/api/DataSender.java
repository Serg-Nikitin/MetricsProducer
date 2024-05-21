package ru.nikitin.kafka.api;


import ru.nikitin.kafka.model.Metrics;

public interface DataSender {
    void send(Metrics request);
}
