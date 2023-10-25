package shop.lotfresh.paymentservice.domain.refund.dto;

import java.time.LocalDateTime;
import java.util.List;

public class RefundPageResponseDTO {
    List<RefundResponseDTO> refundResponseDTO;




    public class RefundResponseDTO {
        private Long refundId;
        private LocalDateTime refundCreatedAt;
        private LocalDateTime paymentCreatedAt;
        private Long orderDetailId;

        private Long stock;
        private Long amount;
        private Long refundStatus;

    }
}
