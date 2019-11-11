package io.microsamples.messaging.customscalingstarter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

@Configuration
@ConditionalOnBean(MetricHandler.class)
@Slf4j
public class CustomScalingAutoconfiguration {

    private MetricHandler metricHandler;

    public CustomScalingAutoconfiguration(MetricHandler metricHandler) {
        this.metricHandler = metricHandler;
    }

    @StreamListener(MetricsSink.INPUT)
    public void handleMessage(Message<NrDbResponse> message) {
        log.info("Received: {}.", message.getPayload());
        metricHandler.process(message.getPayload());
    }

}

