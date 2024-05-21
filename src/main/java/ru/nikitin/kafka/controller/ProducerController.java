package ru.nikitin.kafka.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.nikitin.kafka.api.DataSender;
import ru.nikitin.kafka.model.Metric;
import ru.nikitin.kafka.model.TypeMetrics;

@Slf4j
@RestController
@RequestMapping(path = "/produce", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProducerController {

    @Autowired
    private DataSender dataService;

    @PostMapping(path = "/metrics/performance")
    public void sendPerformance(@RequestBody Metric metric) {
        log.info("sendTest input name = {}", metric);
        dataService.send(metric, TypeMetrics.PERFORMANCE);
    }

    @PostMapping(path = "/metrics/responsive")
    public void sendResponsive(@RequestBody Metric metric) {
        log.info("sendTest input name = {}", metric);
        dataService.send(metric, TypeMetrics.RESPONSIVE);
    }
}
