package backend.e_commerce.infrastructure.out.pg.toss.response;

import backend.e_commerce.infrastructure.out.pg.toss.response.payment.method.Cancel;
import java.util.List;

public class PaymentCancelResponseDto {
    private List<Cancel> cancels;

    private String orderId;
    private String paymentKey;
    private String method;
    private String status;
    private int totalAmount;
    private int balanceAmount;
}
