package backend.e_commerce.infrastructure.out.persistence.payment;

import backend.e_commerce.application.port.out.PaymentLedgerRepository;
import backend.e_commerce.domain.payment.PaymentLedger;
import backend.e_commerce.infrastructure.out.persistence.payment.entity.PaymentLedgerEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PaymentLedgerRepositoryImpl implements PaymentLedgerRepository {

    private final JpaPaymentLedgerRepository jpaPaymentLedgerRepository;

    @Override
    public PaymentLedger save(PaymentLedger paymentLedger) {
        PaymentLedgerEntity paymentLedgerEntity = PaymentLedgerMapper.fromDomain(paymentLedger);
        jpaPaymentLedgerRepository.save(paymentLedgerEntity);

        return PaymentLedgerMapper.toDomain(paymentLedgerEntity);
    }
}
