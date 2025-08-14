package backend.e_commerce.infrastructure.out.persistence.payment;

import backend.core.common.errorcode.errorcode.PaymentErrorCode;
import backend.core.common.errorcode.execption.PaymentException;
import backend.e_commerce.application.port.out.PaymentPersistencePort;
import backend.e_commerce.domain.payment.Payment;
import backend.e_commerce.infrastructure.out.persistence.payment.entity.PaymentEntity;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PaymentPersistenceAdapter implements PaymentPersistencePort {
    private final JpaPaymentRepository jpaPaymentRepository;

    @Override
    public Payment save(Payment payment) {
        PaymentEntity paymentEntity = PaymentMapper.fromDomainToEntity(payment);

        jpaPaymentRepository.save(paymentEntity);
        return PaymentMapper.fromEntityToDomain(paymentEntity);
    }

    @Override
    public Payment findById(String id) {
        PaymentEntity paymentEntity = jpaPaymentRepository.findById(id)
                .orElseThrow(() -> new PaymentException(
                        PaymentErrorCode.PAYMENT_NOT_FOUND,
                        Map.of("paymentKey", id)
                ));

        return PaymentMapper.fromEntityToDomain(paymentEntity);
    }

    @Override
    public Payment update(Payment payment) {
        if(payment.getPaymentKey() == null) {
            throw new PaymentException(PaymentErrorCode.PAYMENT_KEY_NULL);
        }

        PaymentEntity entity = jpaPaymentRepository.findById(payment.getPaymentKey())
                .orElseThrow(() -> new PaymentException(
                        PaymentErrorCode.PAYMENT_NOT_FOUND,
                        Map.of("paymentKey", payment.getPaymentKey())
                ));

        entity.setPaymentStatus(payment.getPaymentStatus());

        System.out.println("entitiy.getPaymentStatus >> "+entity.getPaymentStatus());

        return PaymentMapper.fromEntityToDomain(entity);
    }
}
