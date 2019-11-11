package io.microsamples.messaging.customscalingstarter;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;


public interface MetricsSink {
    String INPUT = "custom-metrics";

    @Input("custom-metrics")
    SubscribableChannel input();
}
