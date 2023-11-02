package shop.lotfresh.paymentservice.domain.payment.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.ToString;
import shop.lotfresh.paymentservice.domain.payment.entity.Payment;

import java.time.LocalDateTime;

@Getter
@ToString
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class KakaopayReadyResponseVO {
    private String tid;
    private boolean tmsResult;
    private String nextRedirectPcUrl;
    private String nextRedirectMobileUrl;
    private String nextRedirectAppUrl;
    private String androidAppScheme;
    private String iosAppScheme;
    private LocalDateTime createdAt;

    public Payment toEntity(Long userId, Long orderId, Long originalAmount ,Long transactionAmount) {
        return Payment.builder()
                .tid(tid)
                .orderId(orderId)
                .userId(userId)
                .paymentMethod("KAKAOPAY")
                .originalAmount(originalAmount)
                .transactionAmount(transactionAmount)
                .build();
    }
}
