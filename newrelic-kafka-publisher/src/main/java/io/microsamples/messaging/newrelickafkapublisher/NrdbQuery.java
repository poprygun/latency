package io.microsamples.messaging.newrelickafkapublisher;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class NrdbQuery
{
    private List<Result> results;
}
