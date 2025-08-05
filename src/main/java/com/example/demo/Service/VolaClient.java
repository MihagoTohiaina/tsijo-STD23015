package com.example.demo.Service;

import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class VolaClient {
  private final WebClient webClient;
  private final String apiKey;

  public VolaClient(
      WebClient.Builder webClientBuilder,
      @Value("${vola.base.url}") String baseUrl,
      @Value("${vola.api.key}") String apiKey) {
    this.webClient = webClientBuilder.baseUrl(baseUrl).build();
    this.apiKey = apiKey;
  }

  public Mono<PaymentResponse> getPaymentStatus(
      String payerEmail, String pspType, String pspPaymentId) {
    return webClient
        .get()
        .uri(
            uriBuilder ->
                uriBuilder
                    .path("/payment")
                    .queryParam("apiKey", apiKey)
                    .queryParam("payerEmail", payerEmail)
                    .queryParam("pspType", pspType)
                    .queryParam("pspPaymentId", pspPaymentId)
                    .build())
        .retrieve()
        .bodyToMono(PaymentResponse.class)
        .timeout(Duration.ofSeconds(5));
  }
}
