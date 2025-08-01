package backend.e_commerce.infrastructure.out.persistence.payment;

import backend.e_commerce.application.port.out.PaymentRepository;
import backend.e_commerce.domain.payment.Payment;
import backend.e_commerce.infrastructure.out.persistence.payment.entity.PaymentEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PaymentRepositoryImpl implements PaymentRepository {
    private final JpaPaymentRepository jpaPaymentRepository;

    @Override
    public Payment save(Payment payment) {
        PaymentEntity paymentEntity = PaymentEntityMapper.toEntity(payment);

        jpaPaymentRepository.save(paymentEntity);
        return PaymentEntityMapper.toDomain(paymentEntity);
    }

    @Override
    public Payment findById(String id) {
        PaymentEntity paymentEntity = jpaPaymentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Payment not found"));

        return PaymentEntityMapper.toDomain(paymentEntity);
    }

    @Override
    public Payment update(Payment payment) {
        if(payment.getPaymentKey() == null) {
            throw new IllegalArgumentException("Payment key is null");
        }

        PaymentEntity entity = jpaPaymentRepository.findById(payment.getPaymentKey())
                        .orElseThrow(() -> new IllegalArgumentException("Payment not found"));

        entity.setPaymentStatus(payment.getPaymentStatus());

        System.out.println("entitiy.getPaymentStatus >> "+entity.getPaymentStatus());

        return PaymentEntityMapper.toDomain(entity);
    }
}
