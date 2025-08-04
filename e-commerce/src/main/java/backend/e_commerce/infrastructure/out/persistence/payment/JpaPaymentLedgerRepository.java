package backend.e_commerce.infrastructure.out.persistence.payment;

import backend.e_commerce.infrastructure.out.persistence.payment.entity.PaymentLedgerEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaPaymentLedgerRepository extends JpaRepository<PaymentLedgerEntity, Long> {
    Optional<PaymentLedgerEntity> findTopByPaymentKeyOrderByIdDesc(String paymentKey);
    List<PaymentLedgerEntity> findAllByPaymentKey(String paymentKey);
}
