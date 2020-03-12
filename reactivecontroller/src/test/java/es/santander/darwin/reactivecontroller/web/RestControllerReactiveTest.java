package es.santander.darwin.reactivecontroller.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class RestControllerReactiveTest {

    @Autowired
    private WebTestClient testClient;

    @Test
    void getAllClientsTest() {
        testClient.get().uri("/v1/client/all")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(String.class).isEqualTo("Get all clients");
    }

    @Test
    void postAllClientsTest() {
        testClient.post().uri("/v1/client/all")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(String.class).isEqualTo("Post all clients");
    }

    @Test
    void getAllClientsWithHeadersTest() {
        testClient.get().uri("/v1/client/all")
                .header("Session-Id", "sessionId")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(String.class).isEqualTo("Get all client with Headers: sessionId");
    }

    @Test
    void getClientByNameTest() {
        testClient.get().uri("/v1/client/Peter")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(String.class).isEqualTo("Get client with name=Peter");
    }

    @Test
    void getClientByIdTest() {
        testClient.get().uri("/v1/client?id=1")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(String.class).isEqualTo("Get client with id=1");
    }

    @Test
    void newClientTest() {
        testClient.put().uri("/v1/client/2/Oscar")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(String.class).isEqualTo("New client with id=2 and name=Oscar");
    }

    @Test
    void deleteClientTest() {
        testClient.delete().uri("/v1/client/3")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(String.class).isEqualTo("Delete client with id=3");
    }

    @Test
    void findAllClientsTest() {
        testClient.delete().uri("/v1/client/newClients")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(String.class);
    }
}