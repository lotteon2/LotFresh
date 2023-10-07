package shop.lotfresh.paymentservice.domain.refund.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.lotfresh.paymentservice.domain.refund.entity.Refund;

@Repository
public interface RefundRepository extends JpaRepository<Refund, Long> {
}
