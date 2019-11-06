package io.microsamples.latency.client;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface Channels {
    @Output("chachkiesChannel")
    MessageChannel outgoing();
}
