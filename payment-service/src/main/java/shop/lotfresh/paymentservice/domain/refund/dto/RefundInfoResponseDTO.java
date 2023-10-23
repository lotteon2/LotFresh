package shop.lotfresh.paymentservice.domain.refund.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class RefundInfoResponseDTO {
    private LocalDateTime refundCreatedAt;
    private Long refundId;
    private LocalDateTime refundUpdatedAt;

    private Long stock;
    private Long productAmount;
    private String refundMethod;
    private Long refundedAmount;
}
