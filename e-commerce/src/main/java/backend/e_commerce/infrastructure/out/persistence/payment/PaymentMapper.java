package backend.e_commerce.infrastructure.out.persistence.payment;

import backend.e_commerce.domain.payment.Payment;
import backend.e_commerce.domain.payment.PaymentMethod;
import backend.e_commerce.domain.payment.PaymentStatus;
import backend.e_commerce.infrastructure.out.persistence.payment.entity.PaymentEntity;
import backend.e_commerce.infrastructure.out.pg.toss.response.PaymentConfirmResponseDto;

public class PaymentMapper {
    public static Payment fromEntityToDomain(PaymentEntity entity) {
        return Payment.builder()
                .paymentKey(entity.getPaymentKey())
                .paymentMethod(entity.getPaymentMethod())
                .paymentStatus(entity.getPaymentStatus())
                .totalAmount(entity.getTotalAmount())
                .canceledAmount(entity.getCanceledAmount())
                .build();
    }

    public static PaymentEntity fromDomainToEntity(Payment payment) {
        return PaymentEntity.builder()
                .paymentKey(payment.getPaymentKey())
                .paymentMethod(payment.getPaymentMethod())
                .paymentStatus(payment.getPaymentStatus())
                .totalAmount(payment.getTotalAmount())
                .canceledAmount(payment.getCanceledAmount())
                .build();
    }

    public static Payment fromPaymentConfirmResponseDtoToPayment(PaymentConfirmResponseDto responseDto) {
        return Payment.builder()
                .paymentKey(responseDto.getPaymentKey())
                .paymentMethod(PaymentMethod.fromMethodName(responseDto.getMethod()))
                .paymentStatus(PaymentStatus.from(responseDto.getStatus()))
                .totalAmount(responseDto.getTotalAmount())
                .canceledAmount(0)
                .build();
    }
}
