package backend.core.common.errorcode.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PaymentErrorCode implements ErrorCode {
    PAYMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "결제 정보를 찾을 수 없습니다."),
    PAYMENT_KEY_NULL(HttpStatus.BAD_REQUEST, "결제 키가 null입니다."),
    PAYMENT_UPDATE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "결제 정보 업데이트에 실패했습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
