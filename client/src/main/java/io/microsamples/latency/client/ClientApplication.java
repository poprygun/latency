package io.microsamples.latency.client;

import lombok.Data;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class ClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }

}

@RestController
class RemoteChachkiesGetter {

	private RestTemplate restTemplate;

	RemoteChachkiesGetter(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@GetMapping("/remote-chachkies")
	private ResponseEntity<List<Chachkie>> remoteChachkies(){
		final ResponseEntity<Chachkie[]> forEntity = restTemplate.getForEntity("http://localhost:8081/chachkies"
				, Chachkie[].class);
		final List<Chachkie> chachkies = Arrays.asList(forEntity.getBody());
		return ResponseEntity.ok(chachkies);

	}
}
@Data
class Chachkie {
	private UUID id;
	private String name, description;
	private Instant when;
}