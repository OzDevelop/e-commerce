package backend.core.common.errorcode.execption;

import backend.core.common.errorcode.errorcode.ErrorCode;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RestApiException extends RuntimeException {
    private final ErrorCode errorCode;
    private final Map<String, Object> details;
}
