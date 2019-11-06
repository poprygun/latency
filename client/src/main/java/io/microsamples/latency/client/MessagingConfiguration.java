package io.microsamples.latency.client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Configuration
@EnableBinding(Channels.class)
@Profile("messaging")
public class MessagingConfiguration {
}

@RestController
@Profile("messaging")
class MessagingController{

    private final MessageChannel outgoing;

    MessagingController(@Qualifier("chachkiesChannel")MessageChannel outgoing) {
        this.outgoing = outgoing;
    }

    @PostMapping("/enqueue-chachkie")
    private void enqueueChachkie(@RequestBody Chachkie chachkie) {
        outgoing.send(MessageBuilder.withPayload(chachkie).build());
    }
}

@Profile("messaging")
interface Channels {
    @Output("chachkiesChannel")
    MessageChannel outgoing();
}
