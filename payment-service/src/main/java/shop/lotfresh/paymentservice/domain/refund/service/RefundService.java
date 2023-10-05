package shop.lotfresh.paymentservice.domain.refund.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.lotfresh.paymentservice.domain.refund.api.request.RefundCreateRequest;
import shop.lotfresh.paymentservice.domain.refund.repository.RefundRepository;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class RefundService {
    private final RefundRepository refundRepository;

    public Long createRefund(RefundCreateRequest request) {

    }
}
