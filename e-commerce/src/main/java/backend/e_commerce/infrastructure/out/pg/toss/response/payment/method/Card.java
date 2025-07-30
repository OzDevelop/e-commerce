package backend.e_commerce.infrastructure.out.pg.toss.response.payment.method;
/*
 * 카드 결제 정보 (card)
 * 카드로 결제 시 제공되는 카드 관련 정보입니다.
 *
 * amount (number)
 * - 카드사에 결제 요청한 금액입니다. 즉시 할인 금액(discount.amount)이 포함됩니다.
 *
 * issuerCode (string)
 * - 카드 발급사 코드 (2자리). 카드사 코드는 별도 참고.
 *
 * acquirerCode (nullable · string)
 * - 카드 매입사 코드 (2자리). 카드사 코드는 별도 참고.
 *
 * number (string)
 * - 카드 번호 일부 마스킹 처리됨 (최대 20자).
 *
 * installmentPlanMonths (integer)
 * - 할부 개월 수. 일시불이면 0.
 *
 * approveNo (string)
 * - 카드사 승인 번호 (최대 8자).
 *
 * useCardPoint (boolean)
 * - 카드사 포인트 사용 여부.
 *   ※ 특수 포인트나 바우처를 사용할 경우 할부 개월 수가 변경될 수 있음.
 *
 * cardType (string)
 * - 카드 종류: "신용", "체크", "기프트", "미확인" 중 하나.
 *   ※ 해외 카드 사용 또는 간편결제 조합 시 "미확인" 표시됨.
 *
 * ownerType (string)
 * - 카드 소유자 타입: "개인", "법인", "미확인" 중 하나.
 *   ※ 해외 카드 사용 또는 간편결제 조합 시 "미확인" 표시됨.
 *
 * acquireStatus (string)
 * - 카드 결제의 매입 상태:
 *   · READY: 아직 매입 요청되지 않음
 *   · REQUESTED: 매입이 요청됨
 *   · COMPLETED: 매입이 완료됨
 *   · CANCEL_REQUESTED: 매입 취소가 요청됨
 *   · CANCELED: 매입 취소가 완료됨
 *
 * isInterestFree (boolean)
 * - 무이자 할부 여부.
 *
 * interestPayer (nullable · string)
 * - 할부 수수료 부담 주체:
 *   · BUYER: 구매자 부담 (일반 할부)
 *   · CARD_COMPANY: 카드사 부담 (무이자)
 *   · MERCHANT: 가맹점 부담 (무이자)
 *
 * ※ 간편결제 카드라면 별도의 간편결제 응답 가이드를 참고하세요.
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Card {
    private String issuerCode;
    private String acquirerCode;
    private String number;
    private String cardType;
    private String acquireStatus;
    private String approveNo;
    private int amount;
    private boolean isInterestFree;
    private String receiptUrl;
}
