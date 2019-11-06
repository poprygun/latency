package io.microsamples.latency.client;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

@SpringBootApplication
public class ClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }

}

@RestController
@Slf4j
class RemoteChachkiesGetter {

    private RestTemplate restTemplate;
    private MeterRegistry registry;
    private AtomicLong chachkiesCount;
    private static final String CHACHKIES_COUNT_GAUGE = "chachkiesServed";
    private final MessageChannel outgoing;
    @Value("${service.url:http://localhost:8081/chachkies}")
    private String serviceUrl;

    RemoteChachkiesGetter(RestTemplate restTemplate
            , MeterRegistry meterRegistry
            , @Qualifier("chachkiesChannel") MessageChannel messageChannel) {
        this.restTemplate = restTemplate;
        this.registry = meterRegistry;
        this.outgoing = messageChannel;
        this.chachkiesCount = new AtomicLong(0L);
    }

    @PostMapping("/enqueue-chachkie")
    private void enqueueChachkie(@RequestBody Chachkie chachkie) {
        outgoing.send(MessageBuilder.withPayload(chachkie).build());

    }

    @GetMapping("/remote-chachkies")
    private ResponseEntity<List<Chachkie>> remoteChachkies() {
        final ResponseEntity<Chachkie[]> forEntity = restTemplate.getForEntity(serviceUrl
                , Chachkie[].class);

        final List<Chachkie> chachkies = Arrays.asList(forEntity.getBody());

        trackChachkies(chachkies);

        return ResponseEntity.ok(chachkies);

    }

    @GetMapping("/reset-chachkies")
    private HttpStatus resetChachkies() {
        final AtomicLong gauge = registry.gauge(CHACHKIES_COUNT_GAUGE, this.chachkiesCount);
        gauge.set(0L);
        return HttpStatus.OK;
    }

    private void trackChachkies(List<Chachkie> chachkies) {
        chachkies.stream().forEach(c -> log.info(c.toString()));
        final AtomicLong chachkiesGauge = registry.gauge(CHACHKIES_COUNT_GAUGE, this.chachkiesCount);
        chachkiesGauge.addAndGet(chachkies.size());
    }
}

@Data
@ToString
class Chachkie {
    private UUID id;
    private String name, description;
    private Instant when;
}