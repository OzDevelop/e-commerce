package backend.e_commerce.infrastructure.out.pg.toss.response.payment;

import backend.e_commerce.domain.payment.PaymentLedger;
import backend.e_commerce.domain.payment.PaymentMethod;
import backend.e_commerce.domain.payment.PaymentStatus;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PaymentLedgerResponseDto {
    private String paymentKey;
    private PaymentMethod method;
    private PaymentStatus paymentStatus;
    private int totalAmount;
    private int canceledAmount;
    private int balanceAmount;
    private int payoutAmount;

    public static PaymentLedgerResponseDto from(PaymentLedger ledger) {
        return PaymentLedgerResponseDto.builder()
                .paymentKey(ledger.getPaymentKey())
                .method(ledger.getMethod())
                .paymentStatus(ledger.getPaymentStatus())
                .totalAmount(ledger.getTotalAmount())
                .canceledAmount(ledger.getCanceledAmount())
                .balanceAmount(ledger.getBalanceAmount())
                .payoutAmount(ledger.getPayoutAmount())
                .build();
    }

    public static List<PaymentLedgerResponseDto> fromList(List<PaymentLedger> ledgers) {
        return ledgers.stream()
                .map(PaymentLedgerResponseDto::from)
                .collect(Collectors.toList());
    }
}
