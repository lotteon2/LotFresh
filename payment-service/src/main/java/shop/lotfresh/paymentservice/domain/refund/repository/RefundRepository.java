package shop.lotfresh.paymentservice.domain.refund.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import shop.lotfresh.paymentservice.domain.refund.entity.Refund;

@Repository
public interface RefundRepository extends JpaRepository<Refund, Long> {
    boolean existsByOrderDetailId(Long orderDetailId);

    // TODO: QueryDSL 통해 타입세이프한 구현필요
    @Query("SELECT SUM(r.amount) FROM Refund r WHERE r.payment.id = :paymentId AND r.status = 'APPROVED'")
    Long findTotalRefundedAmountByPaymentId(@Param("paymentId") Long paymentId);
}
