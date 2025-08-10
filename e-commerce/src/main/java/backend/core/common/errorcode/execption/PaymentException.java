package backend.core.common.errorcode.execption;

import backend.core.common.errorcode.errorcode.PaymentErrorCode;
import backend.core.common.errorcode.errorcode.UserErrorCode;
import java.util.Map;

public class PaymentException extends RestApiException{

    public PaymentException(PaymentErrorCode errorCode) {
        super(errorCode, null);
    }

    public PaymentException(PaymentErrorCode errorCode, Map<String, Object> details) {
        super(errorCode, details);
    }
}
