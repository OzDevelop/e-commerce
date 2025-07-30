package backend.e_commerce.application.port.out.api;

import backend.e_commerce.infrastructure.out.pg.toss.response.PaymentCancelResponseDto;
import backend.e_commerce.infrastructure.out.pg.toss.response.PaymentConfirmResponseDto;
import backend.e_commerce.representaion.request.payment.PaymentCancelRequestDto;
import backend.e_commerce.representaion.request.payment.PaymentConfirmRequestDto;

public interface PaymentAPIs {
    PaymentConfirmResponseDto requestPaymentConfirm(PaymentConfirmRequestDto paymentConfirmRequestDto);
    PaymentCancelResponseDto requestPaymentCancel(String paymentKey, PaymentCancelRequestDto paymentCancelRequestDto);

    boolean isPaymentConfirmed(String status);
}
