package backend.core.common.errorcode.execption;

import backend.core.common.errorcode.errorcode.UserErrorCode;

public class ProductException extends RestApiException{
    public ProductException(UserErrorCode errorCode) {
        super(errorCode);
    }
}
