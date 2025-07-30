package backend.e_commerce.infrastructure.out.pg.toss;

import java.io.IOException;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

public class PaymentExceptionInterceptor implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) {
        try {
            return execution.execute(request, body);
        } catch (IOException e) {
            // TODO - Custom Exception 적용 (PaymentTimeoutException(e))
        } catch (Exception e) {
            // TODO - Custom Exception 적용 (PaymentConfirmException(e))
            throw new RuntimeException(e);
        }
        return null;
    }
}
