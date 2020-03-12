package es.santander.darwin.webclient.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/webclient")
public class WebController {

    @Autowired
    private RestTemplate restTemplate;
    private WebClient webClient;

    private static final String URL = "https://test-micro-reactive-san-narq-ref-dev.appls.boae.paas.gsnetcloud.corp/generic";

    public WebController(WebClient.Builder webClientBuilder) {
        this.webClient= webClientBuilder.baseUrl(URL).build();
    }

    @GetMapping("/status-blocking")
    public String getStatusBlocking(@RequestHeader("Session-Id") String sessionId, @RequestParam(value = "status", defaultValue = "200") int status) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Session-Id", sessionId);
        HttpEntity entity = new HttpEntity(headers);

        ResponseEntity<String> response = this.restTemplate.exchange(URL + "/status/" + status, HttpMethod.GET, entity, String.class);
        HttpStatus statusCode = response.getStatusCode();
        if (statusCode.is4xxClientError() || statusCode.is5xxServerError())
            throw new HttpClientErrorException(statusCode);
        return response.getBody();
    }

    @GetMapping("/status-noblocking")
    public Mono<String> getStatusNonBlocking(@RequestHeader("Session-Id") String sessionId, @RequestParam(value = "status", defaultValue = "200") int status) {
        return  this.webClient.get()
                    .uri("/status/{status}", status)
                    .header("Session-Id", sessionId)
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError,
                        response -> Mono.error(new HttpClientErrorException(response.statusCode())))
                    .onStatus(HttpStatus::is5xxServerError,
                        response -> Mono.error(new HttpClientErrorException(response.statusCode())))
                    .bodyToMono(String.class);
    }

    @GetMapping("/status-noblocking-exchange")
    public Mono<String> getStatusExchange (@RequestHeader("Session-Id") String sessionId, @RequestParam(value = "status", defaultValue = "200") int status) {
        return  this.webClient.get()
                    .uri("/status/{status}", status)
                    .header("Session-Id", sessionId)
                    .exchange()
                    .flatMap(response -> {
                        if (response.statusCode().is4xxClientError())
                            return Mono.error(new HttpClientErrorException(response.statusCode()));
                        if (response.statusCode().is5xxServerError())
                            return Mono.error(new HttpClientErrorException(response.statusCode()));
                        return response.bodyToMono(String.class);
                    });
    }
}