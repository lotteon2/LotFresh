package shop.lotfresh.paymentservice.domain.refund.vo;

import lombok.Getter;

import java.time.LocalDateTime;

public class KakaopayRefundResponseVO {

    private String aid;
    private String tid;
    private String cid;
    private String status;
    private String partnerOrderId;
    private String partnerUserId;
    private String paymentMethodType;

    // 아래 4개의 nested json은 같은 필드이름들을 쓰고 있어서 통일했음.
    private Amount amount;                 // 현재 요청 트랜잭션
    private Amount approvedCancelAmount;   // 현재 트랜잭션으로 인한 취소금액
    private Amount canceledAmount;         // 누적 취소금액
    private Amount cancelAvailableAmount;  // 남은 취소 가능 금액

    private String itemName;
    private Integer quantity;
    private LocalDateTime createdAt;
    private LocalDateTime approvedAt;
    private LocalDateTime canceledAt;

    @Getter
    public static class Amount {
        private Integer total, taxFree, vat, point, discount, greenDeposit;
    }
}
