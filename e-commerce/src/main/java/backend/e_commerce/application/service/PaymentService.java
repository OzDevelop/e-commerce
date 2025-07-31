package backend.e_commerce.application.service;

import backend.e_commerce.application.command.payment.PaymentApprovedCommand;
import backend.e_commerce.application.command.payment.PaymentCancelledCommand;
import backend.e_commerce.application.port.in.payment.PaymentCommandUseCase;
import backend.e_commerce.application.port.in.payment.PaymentQueryUseCase;
import backend.e_commerce.application.port.out.OrderRepository;
import backend.e_commerce.application.port.out.PaymentLedgerRepository;
import backend.e_commerce.application.port.out.PaymentRepository;
import backend.e_commerce.application.port.out.ProductRepository;
import backend.e_commerce.application.port.out.api.PaymentAPIs;
import backend.e_commerce.domain.order.Order;
import backend.e_commerce.domain.order.OrderItem;
import backend.e_commerce.domain.order.OrderStatus;
import backend.e_commerce.domain.payment.Payment;
import backend.e_commerce.domain.payment.PaymentLedger;
import backend.e_commerce.domain.payment.PaymentMethod;
import backend.e_commerce.domain.payment.PaymentStatus;
import backend.e_commerce.domain.product.Product;
import backend.e_commerce.infrastructure.out.pg.toss.response.PaymentCancelResponseDto;
import backend.e_commerce.infrastructure.out.pg.toss.response.PaymentConfirmResponseDto;
import backend.e_commerce.representaion.request.payment.PaymentCancelRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService implements PaymentCommandUseCase, PaymentQueryUseCase {
    private final PaymentAPIs tossPayment;

    private final OrderService orderService;

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentLedgerRepository paymentLedgerRepository;



    @Override
    @Transactional
    public String paymentApproved(PaymentApprovedCommand command) {
        ///  1. 주문이 완료되었는지 확인 (status가 complete 인지)
        System.out.println("UUID: " + command.getOrderId());
        isCompletedOrder(UUID.fromString(command.getOrderId()));
        ///  2. PG사로 결제 승인 요청 보내기
        PaymentConfirmResponseDto responseDto = tossPayment.requestPaymentConfirm(command.toPaymentConfirmRequestDto());

        ///  3. 승인이 되었다면 ( 그 전에 PaymentStatus가 Done으로 변경되어야 함.)
        if (tossPayment.isPaymentConfirmed(responseDto.getStatus())) {
        ///  3-2. 결제원장(ledger) 저장, 주문상태 변경 -> 결제 완료
        Order completedOrder = orderRepository.findById(UUID.fromString(responseDto.getOrderId()))
                .orElseThrow(); // TODO - Exception 처리 필요
        completedOrder.completeOrder();
        completedOrder.setPaymentKey(responseDto.getPaymentKey());
        orderRepository.update(completedOrder);

        // TODO - 추후 삭제 ( 개발 시 확인위한 로깅)
            try {
                System.out.println("응답 전체: " + new ObjectMapper().writeValueAsString(responseDto));
            } catch (Exception e) {
                e.printStackTrace();
            }

            // 결제원장 저장
        paymentLedgerRepository.save(responseDto.toPaymentLedgerDomain());
            // payment 저장
            Payment payment = Payment.builder()
                    .paymentKey(responseDto.getPaymentKey())
                    .paymentMethod(PaymentMethod.fromMethodName(responseDto.getMethod()))
                    .paymentStatus(PaymentStatus.from(responseDto.getStatus()))
                    .totalAmount(responseDto.getTotalAmount())
                    .canceledAmount(0)
                    .build();

            paymentRepository.save(payment);
            /**
             * -- 구매 성공한 상품의 Product 재고 감소 --
             * order 시 구매하려는 product 의 재고가 충분한지 확인 후
             * paymentApprove 시 재고 감소
             */
             List<OrderItem> orderItems = completedOrder.getOrderItems();
             orderItems.forEach(orderItem -> {

                 Long productId = orderItem.getProductId();
                 Product product = productRepository.findById(productId)
                         .orElseThrow();

                 product.decreaseStock(orderItem.getQuantity());
                 productRepository.update(product);
             });


            return "success";
        }

        return "fail";
    }

    @Override
    public boolean paymentCancel(PaymentCancelledCommand command) {
        String paymentKey = command.getPaymentKey();

        Order wantedCancelOrder = orderService.getOrderInfo(command.getOrderId());
        PaymentLedger paymentLedger = getLastPaymentLedger(paymentKey);
        if(!(wantedCancelOrder.getOrderStatus() == OrderStatus.COMPLETED)) {
            PaymentCancelRequestDto requestDto = new PaymentCancelRequestDto(command.getCancelReason(), command.getCancellationAmount());

            PaymentCancelResponseDto responseDto = tossPayment.requestPaymentCancel(paymentKey, requestDto);

            paymentLedgerRepository.save(responseDto.toPaymentLedgerDomain());

            if(command.getItemIds() != null || command.getItemIds().length > 0 ) {
                wantedCancelOrder.orderCancel(command.getItemIds());
            } else {
                wantedCancelOrder.orderAllCancel();
            }
            return true;

        }
        return false;
    }

    private void isCompletedOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        OrderStatus status = order.getOrderStatus();

        if (!status.equals(OrderStatus.PENDING_PAYMENT)) {
            throw new IllegalArgumentException("주문이 완료되지 않았거나, 결제 준비가 되지 않음");
        }
    }

    @Override
    public PaymentLedger getLastPaymentLedger(String paymentKey) {
        return paymentLedgerRepository.findOneByPaymentKeyDesc(paymentKey);
    }
}
