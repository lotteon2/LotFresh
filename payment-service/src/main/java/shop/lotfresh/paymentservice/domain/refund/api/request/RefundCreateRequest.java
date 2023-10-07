package shop.lotfresh.paymentservice.domain.refund.api.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import shop.lotfresh.paymentservice.domain.payment.entity.Payment;
import shop.lotfresh.paymentservice.domain.payment.repository.PaymentRepository;
import shop.lotfresh.paymentservice.domain.refund.entity.Refund;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class RefundCreateRequest {
    @NotNull(message = "orderDetailId cannot be null")
    private Long orderDetailId;

    @NotNull(message = "amount cannot be null")
    private Long amount;

    // orderId를 이용해서 payment table의 payment를 찾고, 그걸 연결해주려고 했음.
    // 내 DB에서 ACID가 만족하는걸 최대한 이용해야함.
    // 객체 연결이 되기 때문에 내 DB에서 payment_id와 oauth_id를 찾아 연결해준다.
    // (FE 이용해서 paymentId를 주던지 orderId를 주면 됨. 어차피 PaymentRepository 이용해서 객체 연결을 해야함.)
    // 아닌가.. FE한테 그냥 PaymentId받으면 되는건데 내가 굳이 OrderId받는건가?
    @NotNull(message = "orderId cannot be null")
    private Long orderId;

    @NotEmpty(message = "refundMethod cannot be empty")
    private String refundMethod;

    @NotEmpty(message = "refundReason cannot be empty")
    private String refundReason;

    public Refund toEntity(Payment payment) {
        return Refund.builder()
                .amount(amount)
                .refundReason(refundReason)
                .refundMethod(refundMethod)
                .orderDetailId(orderDetailId)
                .payment(payment)
                .build();
    }

}
