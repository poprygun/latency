## Latency test

1. Start service application.
2. Start client application.
3. Start RabbitMQ
```bash
docker run -d --name some-rabbit -p 5672:5672 -p 5673:5673 -p 15672:15672 rabbitmq:3-management
```
4. Run Gatling tests

From load directory:

```bash
 mvn gatling:test
```