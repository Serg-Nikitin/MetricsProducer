package ru.nikitin.kafka.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nikitin.kafka.api.DataSender;
import ru.nikitin.kafka.model.MetricValue;
import ru.nikitin.kafka.model.Metrics;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/produce", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProducerController {

    @Autowired
    private DataSender dataService;

    @PostMapping(path = "/metrics")
    public void sendMetrics(@RequestBody Metrics request) {
        log.info("sendTest input name = {}", request);
        dataService.send(request);
    }
}
