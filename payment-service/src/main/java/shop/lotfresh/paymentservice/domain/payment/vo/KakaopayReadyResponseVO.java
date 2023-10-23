package shop.lotfresh.paymentservice.domain.payment.vo;

import lombok.Getter;
import shop.lotfresh.paymentservice.domain.payment.entity.Payment;

import java.time.LocalDateTime;

@Getter
public class KakaopayReadyResponseVO {
    private String tid;
    private boolean tmsResult;
    private String nextRedirectPcUrl;
    private String nextRedirectMobileUrl;
    private String nextRedirectAppUrl;
    private String androidAppScheme;
    private String iosAppScheme;
    private LocalDateTime createdAt;

    public Payment toEntity(Long oauthId, Long orderId, Long originalAmount ,Long transactionAmount) {
        return Payment.builder()
                .tid(tid)
                .orderId(orderId)
                .oauthId(oauthId)
                .paymentMethod("KAKAOPAY")
                .originalAmount(originalAmount)
                .transactionAmount(transactionAmount)
                .build();
    }
}
