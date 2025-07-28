package backend.e_commerce.application.service;

import backend.e_commerce.application.command.payment.PaymentApprovedCommand;
import backend.e_commerce.application.command.payment.PaymentCancelledCommand;
import backend.e_commerce.application.port.in.payment.PaymentCommandUseCase;
import backend.e_commerce.application.port.out.OrderRepository;
import backend.e_commerce.application.port.out.PaymentLedgerRepository;
import backend.e_commerce.application.port.out.api.PaymentAPIs;
import backend.e_commerce.domain.order.Order;
import backend.e_commerce.domain.order.OrderStatus;
import backend.e_commerce.infrastructure.out.pg.toss.response.PaymentConfirmResponseDto;
import backend.e_commerce.representaion.request.payment.PaymentConfirmRequestDto;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService implements PaymentCommandUseCase {
    private final PaymentAPIs tossPayment;

    private final OrderRepository orderRepository;
    private PaymentLedgerRepository paymentLedgerRepository;


    @Override
    public String paymentApproved(PaymentApprovedCommand command) {
        ///  1. 주문이 완료되었는지 확인 (status가 complete 인지)
        isCompletedOrder(UUID.fromString(command.getOrderId()));
        ///  2. PG사로 결제 승인 요청 보내기
        PaymentConfirmResponseDto responseDto = tossPayment.requestPaymentConfirm(command.toPaymentConfirmRequestDto());

        ///  3. 승인이 되었다면
        if (tossPayment.isPaymentConfirmed(responseDto.getPaymentStatus())) {
        ///  3-2. 결제원장(ledger) 저장, 주문상태 변경 -> 결제 완료
        Order completedOrder = orderRepository.findById(UUID.fromString(responseDto.getOrderId()))
                .orElseThrow();
        completedOrder.completeOrder();

        // 결제원장 저장
        paymentLedgerRepository.save(responseDto.toPaymentLedgerEntity());

            /**
             * Order 상태 수정 저장,
             * payment 수정 저장,
             * Ledger 생성,
             */

        }

        return "";
    }

    @Override
    public String paymentCancel(PaymentCancelledCommand command) {
        return "";
    }

    private void isCompletedOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        OrderStatus status = order.getOrderStatus();

        if (!status.equals(OrderStatus.COMPLETED)) {
            throw new IllegalArgumentException("주문이 완료되지 않았거나, 결제 준비가 되지 않음");
        }
    }
}
