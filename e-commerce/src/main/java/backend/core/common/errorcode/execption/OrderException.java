package backend.core.common.errorcode.execption;

import backend.core.common.errorcode.errorcode.UserErrorCode;

public class OrderException extends RestApiException{
    public OrderException(UserErrorCode errorCode) {
        super(errorCode);
    }
}
