package io.microsamples.messaging.newrelickafkapublisher;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "newrelic-client")
public interface NewRelicClient {
    @GetMapping("/graphql")
    NrDbResponse appDataFromNewRelic();
}
