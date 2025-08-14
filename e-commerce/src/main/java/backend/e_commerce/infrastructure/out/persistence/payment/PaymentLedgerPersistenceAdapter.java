package backend.e_commerce.infrastructure.out.persistence.payment;

import backend.e_commerce.application.port.out.PaymentLedgerPersistencePort;
import backend.e_commerce.domain.payment.PaymentLedger;
import backend.e_commerce.infrastructure.out.persistence.payment.entity.PaymentLedgerEntity;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PaymentLedgerPersistenceAdapter implements PaymentLedgerPersistencePort {

    private final JpaPaymentLedgerRepository jpaPaymentLedgerRepository;

    @Override
    public PaymentLedger save(PaymentLedger paymentLedger) {
        PaymentLedgerEntity paymentLedgerEntity = PaymentLedgerMapper.fromDomainToEntity(paymentLedger);
        jpaPaymentLedgerRepository.save(paymentLedgerEntity);

        return PaymentLedgerMapper.fromEntityToDomain(paymentLedgerEntity);
    }

    @Override
    public PaymentLedger findOneByPaymentKeyDesc(String paymentKey) {
        PaymentLedgerEntity entity = jpaPaymentLedgerRepository.findTopByPaymentKeyOrderByIdDesc(paymentKey)
                .orElseThrow(() -> new NullPointerException("findOneByPaymentKeyDesc ::: Not found Payment Transaction"));
        return  PaymentLedgerMapper.fromEntityToDomain(entity);
    }

    @Override
    public List<PaymentLedger> findAllByPaymentKey(String paymentKey) {
        List<PaymentLedgerEntity> entities = jpaPaymentLedgerRepository.findAllByPaymentKey(paymentKey);

        return PaymentLedgerMapper.fromEntityListToDomainList(entities);
    }
}
