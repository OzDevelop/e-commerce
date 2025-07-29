package backend.e_commerce.application.port.out;

import backend.e_commerce.domain.payment.PaymentLedger;

public interface PaymentLedgerRepository {
    PaymentLedger save(PaymentLedger paymentLedger);
    PaymentLedger findOneByPaymentKeyDesc(String paymentKey);
}
