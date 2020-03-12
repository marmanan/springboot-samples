package es.santander.darwin.webclient.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class WebControllerTest {

    @Autowired
    private WebTestClient testClient;

    @Test
    public void getStatusBlocking() {
        testClient.get().uri("/webclient/status-blocking")
                .header("Session-Id", "sessionId")
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    public void getStatusNonBlocking() {
        testClient.get().uri("/webclient/status-noblocking")
                        .header("Session-Id", "sessionId")
                        .exchange()
                        .expectStatus()
                        .isOk()
                        .expectBody(String.class).isEqualTo("Failed with 200");
    }


    @Test
    public void getStatusExchange() {
        testClient.get().uri("/webclient/status-noblocking-exchange")
                .header("Session-Id", "sessionId")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(String.class)
                .consumeWith(r -> {
                    assertThat(r.getResponseBody()).contains("200");
                });
    }
}