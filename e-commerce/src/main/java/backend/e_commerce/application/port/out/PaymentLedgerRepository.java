package backend.e_commerce.application.port.out;

import backend.e_commerce.domain.payment.PaymentLedger;
import java.util.List;

public interface PaymentLedgerRepository {
    PaymentLedger save(PaymentLedger paymentLedger);
    PaymentLedger findOneByPaymentKeyDesc(String paymentKey);
    List<PaymentLedger> findAllByPaymentKey(String paymentKey);
}
