package backend.e_commerce.infrastructure.out.persistence.payment;

import backend.e_commerce.infrastructure.out.persistence.payment.entity.PaymentLedgerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaPaymentLedgerRepository extends JpaRepository<PaymentLedgerEntity, Long> {
}
