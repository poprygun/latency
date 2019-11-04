package io.microsamples.latency.client;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.Data;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.*;
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

    @Value("${service.url:http://localhost:8081/chachkies}")
    private String serviceUrl;

    RemoteChachkiesGetter(RestTemplate restTemplate
            , MeterRegistry meterRegistry) {
        this.restTemplate = restTemplate;
        this.registry = meterRegistry;
        this.chachkiesCount = new AtomicLong(0L);
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