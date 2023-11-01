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

    @NotNull(message = "amount cannot be null")
    private Long stock;

    // 돈 얼마 환불할지 클라이언트한테 직접 받는게 맞나? orderDetailId가 있으면 얼마를 환불해줘야할지 알 수 있는데?
    // 이 정보는 다른 Micro Service에 있다.
    // 일단 openfeign으로 얻어오는걸로 1차적인 결론을 냈음.
    // 현재는 이렇게 작성하지만, 추후 orderDetail쪽에게 이런게 있니? 있다면 얼마짜리니? 를 받아올것임.
    @NotNull(message = "amount cannot be null")
    private Long amount;

    // orderId를 이용해서 payment table의 payment를 찾고, 그걸 연결해주려고 했음.
    // 내 DB에서 ACID가 만족하는걸 최대한 이용해야함.
    // 객체 연결이 되기 때문에 내 DB에서 payment_id와 user_id를 찾아 연결해준다.
    // (FE 이용해서 paymentId를 주던지 orderId를 주면 됨. 어차피 PaymentRepository 이용해서 객체 연결을 해야함.)
    // 아닌가.. FE한테 그냥 PaymentId받으면 되는건데 내가 굳이 OrderId받는건가?
    @NotNull(message = "orderId cannot be null")
    private Long orderId;

    @NotEmpty(message = "refundMethod cannot be empty")
    private String refundMethod;

    @NotEmpty(message = "refundReason cannot be empty")
    private String refundReason;

    public Refund toEntity(Long orderDetailId, Payment payment) {
        return Refund.builder()
                .stock(stock)
                .amount(amount)
                .refundReason(refundReason)
                .refundMethod(refundMethod)
                .orderDetailId(orderDetailId)
                .payment(payment)
                .build();
    }

}
