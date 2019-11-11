package io.microsamples.latency.service;

import io.micrometer.core.instrument.MeterRegistry;
import io.microsamples.messaging.customscalingstarter.MetricHandler;
import io.microsamples.messaging.customscalingstarter.MetricsSink;
import io.microsamples.messaging.customscalingstarter.NrDbResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@Configuration
@EnableBinding(MetricsSink.class)
@Profile("custom-metrics")
public class ScalingConfiguration {
    static final String REQUEST_COUNT_GAUGE = "requestsServed";
    @Bean
    public RequestCountMetricHandler loggingMetricHandler(MeterRegistry registry
            , AtomicLong requestCount) {
        return new RequestCountMetricHandler(registry, requestCount);
    }

    @Bean
    public AtomicLong requestCounter(){
        return new AtomicLong(0L);
    }
}

@RestController
@Slf4j
class ScalingMetricsController{
    private MeterRegistry registry;
    private AtomicLong requestCount;
    ScalingMetricsController(MeterRegistry registry, AtomicLong requestCount) {
        this.registry = registry;
        this.requestCount = requestCount;
    }

    @GetMapping("/reset-scaling-metric")
    public ResponseEntity<String> resetScalingMetric() {
        final AtomicLong gauge = registry.gauge(ScalingConfiguration.REQUEST_COUNT_GAUGE, requestCount);
        gauge.set(0L);

        log.info("Resetting {} to 0", ScalingConfiguration.REQUEST_COUNT_GAUGE);
        return ResponseEntity.ok("Reset " + ScalingConfiguration.REQUEST_COUNT_GAUGE);
    }
}

@Slf4j
class RequestCountMetricHandler implements MetricHandler {
    private MeterRegistry registry;
    private AtomicLong requestCount;

    RequestCountMetricHandler(MeterRegistry registry, AtomicLong requestCount) {
        this.registry = registry;
        this.requestCount = requestCount;
    }

    @Override
    public void process(NrDbResponse responseConsumer) {
        final AtomicLong gauge = registry.gauge(ScalingConfiguration.REQUEST_COUNT_GAUGE, requestCount);
        gauge.set(responseConsumer.getNrdbQuery().getResults().size());
        log.info("Request count ---> {}", gauge.get());
    }
}
