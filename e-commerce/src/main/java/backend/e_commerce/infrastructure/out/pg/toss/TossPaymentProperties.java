package backend.e_commerce.infrastructure.out.pg.toss;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "payment")
@Getter
@Setter
public class TossPaymentProperties {
    private String secretKey;
    private String baseUrl;
    private String confirmEndpoint;
    private String cancelEndpoint;

    public String getConfirmUrl() {
        return baseUrl + confirmEndpoint;
    }

    public String getCancelUrl(String paymentKey) {
        return String.format(baseUrl + cancelEndpoint, paymentKey);
    }
}
