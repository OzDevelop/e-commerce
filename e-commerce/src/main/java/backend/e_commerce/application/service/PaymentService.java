package backend.e_commerce.application.service;

import backend.e_commerce.application.command.payment.PaymentApprovedCommand;
import backend.e_commerce.application.command.payment.PaymentCancelledCommand;
import backend.e_commerce.application.port.in.payment.PaymentCommandUseCase;

public class PaymentService implements PaymentCommandUseCase {

    @Override
    public String paymentApproved(PaymentApprovedCommand command) {
        return "";
    }

    @Override
    public String paymentCancel(PaymentCancelledCommand command) {
        return "";
    }
}
