package shop.lotfresh.paymentservice.domain.payment.webclient;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import shop.lotfresh.paymentservice.domain.payment.vo.*;


@Component
public class KakaopayPaymentApiClient {
    private final WebClient webClient;

    @Value("${kakaopay.admin_key}")
    private String KAKAOPAY_SERVICE_APP_ADMIN_KEY;

    public KakaopayPaymentApiClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://kapi.kakao.com").build();
    }

    public KakaopayReadyResponseVO kakaopayReady(Long orderId, KakaopayReadyVO request) {
        String adminKey = "KakaoAK " + KAKAOPAY_SERVICE_APP_ADMIN_KEY;

        return webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1/payment/ready")
                        .queryParam("orderId", orderId)
                        .build())
                .header("Authorization", adminKey)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue(request.toMultiValueMap()) // 요청 형식에 맞게 객체를 변환
                .retrieve()
                .bodyToMono(KakaopayReadyResponseVO.class)
                .block();
    }

    public KakaopayApproveResponseVO kakaopayApprove(KakaopayApproveVO request) {
        String adminKey = "KakaoAK " + KAKAOPAY_SERVICE_APP_ADMIN_KEY;

        return webClient.post()
                .uri("/v1/payment/approve")
                .header("Authorization", adminKey)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue(request.toMultiValueMap()) // 요청 형식에 맞게 객체를 변환
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse ->
                        clientResponse.bodyToMono(KakaopayApproveErrorResponseVO.class)
                                .flatMap(errorBody -> Mono.error(new RuntimeException(
                                        "Error during payment approval: Code=" + errorBody.getCode() +
                                                ", Message=" + errorBody.getMessage() +
                                                ", Extras=" + errorBody.getExtras()))))
                .bodyToMono(KakaopayApproveResponseVO.class)
                .block();
    }
}
