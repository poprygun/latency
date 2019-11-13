package io.microsamples.messaging.newrelickafkapublisher;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableFeignClients
@EnableBinding(Channels.class)
public class NewrelicKafkaPublisherApplication {
	public static void main(String[] args) {
		SpringApplication.run(NewrelicKafkaPublisherApplication.class, args);
	}
}

@RestController
@Slf4j
class ClientController {
	private final NewRelicClient newRelicClient;
	private final MessageChannel outgoing;

	ClientController(NewRelicClient newRelicClient, @Qualifier("customMetricsChannel") MessageChannel outgoing) {
		this.newRelicClient = newRelicClient;
		this.outgoing = outgoing;
	}

	@GetMapping("/enqueue")
	private ResponseEntity<NrDbResponse> enqueue(){
		final NrDbResponse body = newRelicClient.appDataFromNewRelic();
		outgoing.send(MessageBuilder.withPayload(body).build());
		return ResponseEntity.ok(body);
	}
}

interface Channels {
	@Output("customMetricsChannel")
	MessageChannel outgoing();
}
