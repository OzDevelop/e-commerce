package backend.e_commerce.application.port.out.api;

import backend.e_commerce.infrastructure.out.pg.toss.response.PaymentCancelResponseDto;
import backend.e_commerce.infrastructure.out.pg.toss.response.PaymentConfirmResponseDto;
import backend.e_commerce.representaion.request.payment.PaymentCancelRequest;
import backend.e_commerce.representaion.request.payment.PaymentConfirmRequest;

public interface PaymentAPIs {
    PaymentConfirmResponseDto requestPaymentConfirm(PaymentConfirmRequest paymentConfirmRequestDto);
    PaymentCancelResponseDto requestPaymentCancel(String paymentKey, PaymentCancelRequest paymentCancelRequestDto);

    boolean isPaymentConfirmed(String status);
}
