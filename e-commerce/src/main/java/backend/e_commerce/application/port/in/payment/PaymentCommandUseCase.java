package backend.e_commerce.application.port.in.payment;

import backend.e_commerce.application.command.payment.PaymentApprovedCommand;
import backend.e_commerce.application.command.payment.PaymentCancelledCommand;

public interface PaymentCommandUseCase {
    String paymentApproved(PaymentApprovedCommand command);
    String paymentCancel(PaymentCancelledCommand command);
}
