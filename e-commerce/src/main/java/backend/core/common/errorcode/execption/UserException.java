package backend.core.common.errorcode.execption;

import backend.core.common.errorcode.errorcode.UserErrorCode;

public class UserException extends RestApiException{
    public UserException(UserErrorCode errorCode) {
        super(errorCode);
    }
}
