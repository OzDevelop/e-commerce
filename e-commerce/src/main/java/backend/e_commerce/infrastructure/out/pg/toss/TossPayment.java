package backend.e_commerce.infrastructure.out.pg.toss;

import backend.e_commerce.application.port.out.api.PaymentAPIs;
import backend.e_commerce.domain.payment.PaymentStatus;
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

/**
 * status string(https://docs.tosspayments.com/reference#payment-%EA%B0%9D%EC%B2%B4)
 * 결제 처리 상태입니다. 아래와 같은 상태 값을 가질 수 있습니다. 상태 변화 흐름이 궁금하다면 흐름도를 살펴보세요.
 * - READY: 결제를 생성하면 가지게 되는 초기 상태입니다. 인증 전까지는 READY 상태를 유지합니다.
 * - IN_PROGRESS: 결제수단 정보와 해당 결제수단의 소유자가 맞는지 인증을 마친 상태입니다. 결제 승인 API를 호출하면 결제가 완료됩니다.
 * - WAITING_FOR_DEPOSIT: 가상계좌 결제 흐름에만 있는 상태입니다. 발급된 가상계좌에 구매자가 아직 입금하지 않은 상태입니다.
 * - DONE: 인증된 결제수단으로 요청한 결제가 승인된 상태입니다.
 * - CANCELED: 승인된 결제가 취소된 상태입니다.
 * - PARTIAL_CANCELED: 승인된 결제가 부분 취소된 상태입니다.
 * - ABORTED: 결제 승인이 실패한 상태입니다.
 * - EXPIRED: 결제 유효 시간 30분이 지나 거래가 취소된 상태입니다. IN_PROGRESS 상태에서 결제 승인 API를 호출하지 않으면 EXPIRED가 됩니다.
 */

@Component
public class TossPayment implements PaymentAPIs {
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
        return AUTH_HEADER_PREFIX+ " " + new String(encodedBytes);
    }


    private ClientHttpRequestFactory createPaymentReqeustFactory() {
        SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();

        simpleClientHttpRequestFactory.setConnectTimeout(Duration.ofSeconds(CONNECT_TIMEOUT_SECONDS));
        simpleClientHttpRequestFactory.setReadTimeout(Duration.ofSeconds(READ_TIMEOUT_SECONDS));

        return simpleClientHttpRequestFactory;
    }

    @Override
    public PaymentConfirmResponseDto requestPaymentConfirm(PaymentConfirmRequestDto paymentConfirmRequestDto) {
        return restClient.method(HttpMethod.POST)
                .uri(paymentProperties.getConfirmUrl())
                .contentType(MediaType.APPLICATION_JSON)
                .body(paymentConfirmRequestDto)
                .retrieve()
                .body(PaymentConfirmResponseDto.class);
    }

    @Override
    public PaymentCancelResponseDto requestPaymentCancel(String paymentKey, PaymentCancelRequestDto paymentCancelRequestDto) {
        return restClient.method(HttpMethod.POST)
                .uri(paymentProperties.getCancelUrl(paymentKey))
                .contentType(MediaType.APPLICATION_JSON)
                .body(paymentCancelRequestDto)
                .retrieve()
                .body(PaymentCancelResponseDto.class);
    }

    @Override
    public boolean isPaymentConfirmed(String status) {
        return "DONE".equalsIgnoreCase(status);
    }
}
