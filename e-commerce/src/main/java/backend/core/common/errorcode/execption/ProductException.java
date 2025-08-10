package backend.core.common.errorcode.execption;

import backend.core.common.errorcode.errorcode.ProductErrorCode;
import java.util.Map;

public class ProductException extends RestApiException{
    public ProductException(ProductErrorCode errorCode, Map<String, Object> details) {
        super(errorCode, details);
    }
}
