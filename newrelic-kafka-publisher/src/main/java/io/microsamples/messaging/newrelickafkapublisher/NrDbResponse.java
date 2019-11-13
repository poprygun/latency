package io.microsamples.messaging.newrelickafkapublisher;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class NrDbResponse
{
    private NrdbQuery nrdbQuery;
    private String name;
}
