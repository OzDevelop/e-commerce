package backend.core.common.errorcode.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode {
    /** CommonErrorCode */
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "잘못된 파라미터가 포함되었습니다."),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 주소를 찾을 수 없습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "내부 서버 에러."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
