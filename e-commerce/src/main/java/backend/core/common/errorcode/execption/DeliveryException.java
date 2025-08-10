package backend.core.common.errorcode.execption;

import backend.core.common.errorcode.errorcode.DeliveryErrorCode;
import backend.core.common.errorcode.errorcode.UserErrorCode;
import java.util.Map;

public class DeliveryException extends RestApiException {

    public DeliveryException(DeliveryErrorCode errorCode) {
        super(errorCode, null);
    }

    public DeliveryException(DeliveryErrorCode errorCode, Map<String, Object> details) {
        super(errorCode, details);
    }
}
