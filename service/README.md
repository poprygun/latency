# Service Application

## Setup

```bash
# build custom-scaling-starter library
cd custom-scaling-starter
mvn clean install

# build Service Application
cd service
mvn -U clean package
```

## Starting the App

Kafka needs to be running and a topic has to be created before you can use the service application. By default, it will try to use a `custom-metrics` topic but this can be changed in `application-custom-metrics.yml`.

```bash
# After kafka is running...
mvn spring-boot:run -Dspring.profiles.active=custom-metrics
```

## Using the App

The service application automatically subscribes to the `custom-metrics` kafka topic. It will set its custom metric to the number of NrDbResponse/NrDbQuery/Results based on data from kafka.

You can reset the custom metric as follows using `httpie`:

```bash
http localhost:8081/reset-scaling-metric
```

New Relic data can be put onto the Kafka topic using this application: https://github.com/walterscarborough/kafka-newrelic-publisher 🆒