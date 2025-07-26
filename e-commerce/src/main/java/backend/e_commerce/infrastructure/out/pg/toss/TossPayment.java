package backend.e_commerce.infrastructure.out.pg.toss;

import backend.e_commerce.infrastructure.out.pg.toss.response.PaymentCancelResponseDto;
import backend.e_commerce.infrastructure.out.pg.toss.response.PaymentConfirmResponseDto;
import backend.e_commerce.representaion.request.payment.PaymentCancelRequestDto;
import backend.e_commerce.representaion.request.payment.PaymentConfirmRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Base64;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class TossPayment {
    private static final String BASIC_DELIMITER = ":";
    private static final String AUTH_HEADER_PREFIX = "Basic";
    private static final int CONNECT_TIMEOUT_SECONDS = 1;
    private static final int READ_TIMEOUT_SECONDS = 60;

    private final ObjectMapper objectMapper;
    private final TossPaymentProperties paymentProperties;
    private RestClient restClient;

    public TossPayment(ObjectMapper objectMapper, TossPaymentProperties paymentProperties) {
        this.objectMapper = objectMapper;
        this.paymentProperties = paymentProperties;
        this.restClient = RestClient.builder()
                .requestFactory(createPaymentReqeustFactory())
                .requestInterceptor(new PaymentExceptionInterceptor()) // 요청 시 발생하는 예외 처리를 위한 인터셉터
                .defaultHeader(HttpHeaders.AUTHORIZATION, createPaymentAuthHeader(paymentProperties))
                .build();
    }

    private String createPaymentAuthHeader(TossPaymentProperties paymentProperties) {
        byte[] encodedBytes = Base64.getEncoder().encode((paymentProperties.getSecretKey() + BASIC_DELIMITER).getBytes(
                StandardCharsets.UTF_8));
        return AUTH_HEADER_PREFIX + new String(encodedBytes);
    }

    private ClientHttpRequestFactory createPaymentReqeustFactory() {
        SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();

        simpleClientHttpRequestFactory.setConnectTimeout(Duration.ofSeconds(CONNECT_TIMEOUT_SECONDS));
        simpleClientHttpRequestFactory.setReadTimeout(Duration.ofSeconds(READ_TIMEOUT_SECONDS));

        return simpleClientHttpRequestFactory;
    }

    public PaymentConfirmResponseDto requestPaymentConfirm(PaymentConfirmRequestDto paymentConfirmRequestDto) {
        return restClient.method(HttpMethod.POST)
                .uri(paymentProperties.getConfirmEndpoint())
                .contentType(MediaType.APPLICATION_JSON)
                .body(paymentConfirmRequestDto)
                .retrieve()
                .body(PaymentConfirmResponseDto.class);
    }

    public PaymentCancelResponseDto requestPaymentCancel(String paymentKey, PaymentCancelRequestDto paymentCancelRequestDto) {
        return restClient.method(HttpMethod.POST)
                .uri(paymentProperties.getCancelUrl(paymentKey))
                .contentType(MediaType.APPLICATION_JSON)
                .body(paymentCancelRequestDto)
                .retrieve()
                .body(PaymentCancelResponseDto.class);
    }
}
