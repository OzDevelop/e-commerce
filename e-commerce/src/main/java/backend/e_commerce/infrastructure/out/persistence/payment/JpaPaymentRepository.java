package backend.e_commerce.infrastructure.out.persistence.payment;

import backend.e_commerce.infrastructure.out.persistence.payment.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaPaymentRepository extends JpaRepository<PaymentEntity, String> {
}
