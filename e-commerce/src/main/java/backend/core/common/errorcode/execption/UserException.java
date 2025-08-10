package backend.core.common.errorcode.execption;

import backend.core.common.errorcode.errorcode.UserErrorCode;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class UserException extends RestApiException{

    public UserException(UserErrorCode errorCode, Map<String, Object> details) {
        super(errorCode, details);
    }
}
