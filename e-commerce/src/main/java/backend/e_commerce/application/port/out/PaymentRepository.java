package backend.e_commerce.application.port.out;

import backend.e_commerce.domain.payment.Payment;

public interface PaymentRepository  {
    Payment save(Payment payment);

    Payment findById(String id);

    Payment update(Payment payment);
}
