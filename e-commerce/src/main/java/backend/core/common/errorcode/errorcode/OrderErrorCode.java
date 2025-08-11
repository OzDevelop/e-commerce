package backend.core.common.errorcode.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum OrderErrorCode implements ErrorCode {

    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "주문을 찾을 수 없습니다."),
    PRODUCT_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당 상품이 존재하지 않습니다."),
    INSUFFICIENT_STOCK(HttpStatus.BAD_REQUEST, "재고가 부족한 상품입니다."),
    PRODUCT_DISCONTINUED(HttpStatus.BAD_REQUEST, "판매 종료된 상품입니다."),
    PRODUCT_OUT_OF_STOCK(HttpStatus.BAD_REQUEST, "품절된 상품입니다."),
    ALREADY_COMPLETED(HttpStatus.BAD_REQUEST, "이미 완료된 주문입니다.")

    ;

    private final HttpStatus httpStatus;
    private final String message;
}