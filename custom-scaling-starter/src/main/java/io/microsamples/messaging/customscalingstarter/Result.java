package io.microsamples.messaging.customscalingstarter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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
