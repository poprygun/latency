# Load Test

Demonstrates a simple load test that can be used to stress test REST API.

## Prerequisites

1. Start Algebra Service application `io.microsamples.testz.app.AppApplication`

## Run load tests

1. Adjust `io.microsamples.testz.util.Environment` to point to service base url.
2. Adjust `io.microsamples.testz.scenarios.GetRoots` for complete url.
3. From `load\` directory run
```bash
mvn gatling:test

```

Test results are available in `target/gatling/../index.html`