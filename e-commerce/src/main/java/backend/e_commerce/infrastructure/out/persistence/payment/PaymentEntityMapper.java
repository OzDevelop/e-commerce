package backend.e_commerce.infrastructure.out.persistence.payment;

import backend.e_commerce.domain.payment.Payment;
import backend.e_commerce.infrastructure.out.persistence.payment.entity.PaymentEntity;

public class PaymentEntityMapper {
    // Payment
    public static Payment toDomain(PaymentEntity entity) {
        return Payment.builder()
                .paymentKey(entity.getPaymentKey())
                .paymentMethod(entity.getPaymentMethod())
                .paymentStatus(entity.getPaymentStatus())
                .totalAmount(entity.getTotalAmount())
                .canceledAmount(entity.getCanceledAmount())
                .build();
    }

    public static PaymentEntity toEntity(Payment payment) {
        return PaymentEntity.builder()
                .paymentKey(payment.getPaymentKey())
                .paymentMethod(payment.getPaymentMethod())
                .paymentStatus(payment.getPaymentStatus())
                .totalAmount(payment.getTotalAmount())
                .canceledAmount(payment.getCanceledAmount())
                .build();
    }
}
