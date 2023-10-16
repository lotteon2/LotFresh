package shop.lotfresh.paymentservice.webclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import shop.lotfresh.paymentservice.domain.payment.vo.*;
import shop.lotfresh.paymentservice.domain.refund.vo.KakaopayRefundResponseVO;
import shop.lotfresh.paymentservice.domain.refund.vo.KakaopayRefundVO;

@Component
public class KakaopayApiClient {

    private final WebClient webClient;

    @Value("${kakaopay.admin_key}")
    private String KAKAOPAY_SERVICE_APP_ADMIN_KEY;
    private final String ADMIN_KEY = "KakaoAK " + KAKAOPAY_SERVICE_APP_ADMIN_KEY;

    public KakaopayApiClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://kapi.kakao.com").build();
    }

    public KakaopayReadyResponseVO kakaopayReady(Long orderId, KakaopayReadyVO request) {
        return webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1/payment/ready")
                        .queryParam("orderId", orderId)
                        .build())
                .header("Authorization", ADMIN_KEY)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue(request.toMultiValueMap())
                .retrieve()
                .bodyToMono(KakaopayReadyResponseVO.class)
                .block();
        // bodyValue까지가 HTTP request에 대한 명세작성.

        // 실제 네트워크 요청이 발생하고 응답을 받아오는 작업은 retrieve() 메서드가 호출될 때 수행됨.
        // retrieve() 메소드는 ClientResponse 객체를 return함.

        // 이거를 body로 받을때 Mono<T> 혹은 Flux<T>중 뭘로 받을지 bodyToMono, bodyToFlux로 결정함.
        // 괄호 안에 들어가는건 return type의 class

        // 근데 block을 하면 동기적으로 처리하겠다는 뜻이니 return type을 Mono<T>로 받는게 아니라 T로 받는다.

        // 참고: retrieve만 있는게 아니라 exchange라는것도 저 자리에 올 수 있음.
        // 성공적인 응답만 예상하고 그 외 경우에 대해서는 예외 처리하기 원한다면: retrieve()
        // 모든 종류의 응답(성공/실패 포함)에 대해 직접 제어하려면: exchange()
    }

    public KakaopayApproveResponseVO kakaopayApprove(KakaopayApproveVO request) {
        return webClient.post()
                .uri("/v1/payment/approve")
                .header("Authorization", ADMIN_KEY)
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


    public KakaopayRefundResponseVO kakaopayRefund(KakaopayRefundVO request) {
        return webClient.post()
                .uri("/v1/payment/cancel")
                .header("Authorization", ADMIN_KEY)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue(request.toMultiValueMap()) // 요청 형식에 맞게 객체를 변환
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse ->
                        clientResponse.bodyToMono(KakaopayApproveErrorResponseVO.class)
                                .flatMap(errorBody -> Mono.error(new RuntimeException(
                                        "Error during payment approval: Code=" + errorBody.getCode() +
                                                ", Message=" + errorBody.getMessage() +
                                                ", Extras=" + errorBody.getExtras()))))
                .bodyToMono(KakaopayRefundResponseVO.class)
                .block();

    }
}
