package io.microsamples.messaging.newrelickafkapublisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Slf4j
class NewRelicClientTest {

    @Autowired
    private NewRelicClient newRelicClient;

    @Test
    void appDataFromNewRelic() throws JsonProcessingException {
        final NrDbResponse response = newRelicClient.appDataFromNewRelic();
        assertThat(response).isNotNull();
    }
}