package backend.e_commerce.application.port.in.payment;

import backend.e_commerce.domain.payment.PaymentLedger;
import java.util.List;

public interface PaymentQueryUseCase {
    List<PaymentLedger> getPaymentLedger(String paymentKey);
    PaymentLedger getLastPaymentLedger(String paymentKey);

}
