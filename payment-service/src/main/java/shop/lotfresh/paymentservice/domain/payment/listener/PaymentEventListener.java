package shop.lotfresh.paymentservice.domain.payment.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import shop.lotfresh.paymentservice.domain.payment.entity.PaymentStatus;
import shop.lotfresh.paymentservice.domain.payment.listener.message.PaymentAbortMessage;
import shop.lotfresh.paymentservice.domain.payment.service.PaymentService;

@Component
@Slf4j
@RequiredArgsConstructor
public class PaymentEventListener {
    private final PaymentService paymentService;


    @KafkaListener(topics = "payment-abort", groupId = "${spring.kafka.consumer.group-id}")
    public void listenPaymentEvent(ConsumerRecord<Long, Object> record) {
        log.warn("Received message: " + record.toString());
        Long orderId = record.key();
        String message = (String)record.value();

        try {
            PaymentStatus status = PaymentStatus.valueOf(message);
            paymentService.abortPayment(orderId, status);

        } catch (IllegalArgumentException e) {
            log.error("Invalid payment status: " + message, e);
        }

    }


}
