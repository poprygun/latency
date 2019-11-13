package io.microsamples.messaging.newrelickafkapublisher;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import java.time.Instant;

@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Result {

    private String duration, appName;
    private Instant timestamp;
}
