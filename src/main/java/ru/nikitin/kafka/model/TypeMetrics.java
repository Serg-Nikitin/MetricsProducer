package ru.nikitin.kafka.model;

import lombok.Getter;

@Getter
public enum TypeMetrics {
    PERFORMANCE("performance"),
    RESPONSIVE("responsive");
    private final String name;

    TypeMetrics(String typeName) {
        this.name = typeName;
    }
}
