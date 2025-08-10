package backend.core.common.errorcode.execption;

import backend.core.common.errorcode.errorcode.OrderErrorCode;
import backend.core.common.errorcode.errorcode.UserErrorCode;
import java.util.Map;

public class OrderException extends RestApiException{
    public OrderException(OrderErrorCode errorCode) {
        super(errorCode, null);
    }

    public OrderException(OrderErrorCode errorCode, Map<String, Object> details) {
        super(errorCode, details);
    }
}
