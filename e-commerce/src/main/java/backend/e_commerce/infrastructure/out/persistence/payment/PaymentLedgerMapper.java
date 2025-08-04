package backend.e_commerce.infrastructure.out.persistence.payment;

import backend.e_commerce.domain.payment.PaymentLedger;
import backend.e_commerce.infrastructure.out.persistence.payment.entity.PaymentLedgerEntity;
import java.util.List;
import java.util.stream.Collectors;

public class PaymentLedgerMapper {
    public static PaymentLedgerEntity fromDomainToEntity(PaymentLedger paymentLedger) {
        return PaymentLedgerEntity.builder()
                .paymentKey(paymentLedger.getPaymentKey())
                .method(paymentLedger.getMethod())
                .paymentStatus(paymentLedger.getPaymentStatus())
                .totalAmount(paymentLedger.getTotalAmount())
                .canceledAmount(paymentLedger.getCanceledAmount())
                .balanceAmount(paymentLedger.getBalanceAmount())
                .payoutAmount(paymentLedger.getPayoutAmount())
                .build();
    }

    public static PaymentLedger fromEntityToDomain(PaymentLedgerEntity paymentLedgerEntity) {
        return PaymentLedger.builder()
                .paymentKey(paymentLedgerEntity.getPaymentKey())
                .method(paymentLedgerEntity.getMethod())
                .paymentStatus(paymentLedgerEntity.getPaymentStatus())
                .totalAmount(paymentLedgerEntity.getTotalAmount())
                .canceledAmount(paymentLedgerEntity.getCanceledAmount())
                .balanceAmount(paymentLedgerEntity.getBalanceAmount())
                .payoutAmount(paymentLedgerEntity.getPayoutAmount())
                .build();
    }

    public static List<PaymentLedger> fromEntityListToDomainList(List<PaymentLedgerEntity> entityList) {
        return entityList.stream()
                .map(PaymentLedgerMapper::fromEntityToDomain)
                .collect(Collectors.toList());
    }
}
