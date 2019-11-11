package io.microsamples.messaging.customscalingstarter;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class NrdbQuery
{
    private List<Result> results;
}
