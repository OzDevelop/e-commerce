package backend.core.common.errorcode.execption;

import backend.core.common.errorcode.errorcode.UserErrorCode;

public class PaymentException extends RestApiException{
    public PaymentException(UserErrorCode errorCode) {
        super(errorCode);
    }
}
