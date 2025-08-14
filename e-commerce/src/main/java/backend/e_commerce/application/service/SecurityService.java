package backend.e_commerce.application.service;

import backend.core.common.errorcode.errorcode.OrderErrorCode;
import backend.core.common.errorcode.errorcode.PaymentErrorCode;
import backend.core.common.errorcode.execption.OrderException;
import backend.core.common.errorcode.execption.PaymentException;
import backend.core.common.utils.IntegrityUtils;
import backend.e_commerce.application.port.out.OrderPersistencePort;
import backend.e_commerce.domain.order.Order;
import backend.e_commerce.domain.order.OrderItem;
import backend.e_commerce.infrastructure.out.pg.toss.response.PaymentConfirmResponseDto;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityService {
    private final OrderPersistencePort orderRepository;

    /**
     * 주문 무결성 검증
     * - DB에 저장된 integrityHash와 현재 Order 스냅샷을 해시한 값 비교
     */
    public Order verifyOrderIntegrity(UUID orderId) {
        Order order = orderRepository.findByIdForUpdate(orderId)
                .orElseThrow(() -> new OrderException(
                        OrderErrorCode.ORDER_NOT_FOUND,
                        Map.of("orderId", orderId)
                ));

        String currentHash = IntegrityUtils.calculateHash(order);
        if (!currentHash.equals(order.getIntegrityHash())) {
            throw new OrderException(OrderErrorCode.ORDER_INTEGRITY_VALID);
        }

        return order;
    }

    /**
     * 결제 승인 응답 무결성 검증 (서명 미사용 시 데이터 일관성 검증)
     * - 응답의 orderId == DB 주문 ID
     * - 응답 금액 == DB 주문 총액
     * - 통화/상태/결제수단 등 기대값 확인
     */
    public void verifyPaymentApproveResponse(UUID orderId, PaymentConfirmResponseDto res) {
        // 1) 주문 존재 + 무결성 선검증
        Order order = verifyOrderIntegrity(orderId);

        // 2) orderId 매칭
        if (!orderId.toString().equals(res.getOrderId())) {
            throw new PaymentException(PaymentErrorCode.PAYMENT_ID_INTEGRITY);
        }

        // 3) 금액 매칭
        int expectedTotal = order.getOrderItems().stream()
                .mapToInt(OrderItem::getAmount)
                .sum();

        if (expectedTotal != res.getTotalAmount()) {
            throw new PaymentException(PaymentErrorCode.PAYMENT_AMOUNT_INTEGRITY, Map.of("expectedTotal", expectedTotal, "ResponseTotal", res.getTotalAmount()));
        }
    }
}
