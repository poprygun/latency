package io.microsamples.latency.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Configuration
@EnableBinding({Sink.class})
@Profile("messaging")
public class MessagingConfiguration {
}

@Component
@Profile("messaging")
@Slf4j
class ChachkiesConsumer{
    @StreamListener(Sink.INPUT)
    public void handleMessage(Message<Chachkie> message) {
        log.info("Received: {}.", message.getPayload());
    }
}