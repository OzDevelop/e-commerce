package backend.core.common.errorcode.execption;

import backend.core.common.errorcode.errorcode.UserErrorCode;

public class DeliveryException extends RestApiException{
    public DeliveryException(UserErrorCode errorCode) {
        super(errorCode);
    }
}
