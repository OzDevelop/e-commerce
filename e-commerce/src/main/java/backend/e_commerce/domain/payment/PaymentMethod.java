package backend.e_commerce.domain.payment;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Getter;

@Getter
public enum PaymentMethod {
    CARD("카드"),
    CREDIT_CARD("신용 카드"),
    DEBIT_CARD("직불 카드"),
    PAYPAL("페이팔"),
    BANK_TRANSFER("계좌 이체");

    final String desc;

    PaymentMethod(String desc) {
        this.desc = desc;
    }

    private static final Map<String,String> methodMap = Collections.unmodifiableMap(
            Stream.of(values()).collect(Collectors.toMap(PaymentMethod::getDesc, PaymentMethod::name))
    );

    public static PaymentMethod fromMethodName(String methodName) {
        return PaymentMethod.valueOf(methodMap.get(methodName));
    }
}
