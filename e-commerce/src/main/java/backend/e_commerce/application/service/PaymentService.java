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
import backend.e_commerce.representaion.request.payment.PaymentConfirmRequestDto;
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
        UUID orderId = UUID.fromString(command.getOrderId());
        validatePendingOrder(orderId);

        PaymentConfirmResponseDto responseDto = tossPayment.requestPaymentConfirm(command.toPaymentConfirmRequestDto());

        if (!tossPayment.isPaymentConfirmed(responseDto.getStatus())) {
            return "fail";
        }

        Order order = completeOrder(responseDto);
        savePaymentAndLedger(responseDto);
        decreaseProductStock(order.getOrderItems());

        return  "success";
    }

    @Override
    @Transactional
    public boolean paymentCancel(PaymentCancelledCommand command) {
        String paymentKey = command.getPaymentKey();
        int cancellationAmount = command.getCancellationAmount();   // 취소할 금액
        Long[] itemIds = command.getItemIds();

        Order order = orderService.getOrderInfo(command.getOrderId());
        Payment payment = paymentRepository.findById(paymentKey);
        PaymentLedger lastLedger = getLastPaymentLedger(paymentKey);

        if(!isCancellable(order, cancellationAmount, lastLedger)) return false;

        PaymentCancelResponseDto responseDto = tossPayment.requestPaymentCancel(paymentKey, command.toPaymentCancelRequestDto());
        paymentLedgerRepository.save(responseDto.toPaymentLedgerDomain());

        updatePaymentStatus(payment, itemIds, order.getOrderItems().size());
        increaseProductStock(order.getOrderItems());
        cancelOrderItems(order, itemIds);

        orderRepository.update(order);
        paymentRepository.update(payment);

        return true;
    }

    @Override
    public PaymentLedger getLastPaymentLedger(String paymentKey) {
        return paymentLedgerRepository.findOneByPaymentKeyDesc(paymentKey);
    }

    @Override
    public List<PaymentLedger> getPaymentLedger(String paymentKey) {
        return paymentLedgerRepository.findAllByPaymentKey(paymentKey);
    }

    /// ------------------------------------ Private Methods ------------------------------------ ///

    private void validatePendingOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        OrderStatus status = order.getOrderStatus();

        if (!status.equals(OrderStatus.PENDING_PAYMENT)) {
            throw new IllegalArgumentException("주문이 완료되지 않았거나, 결제 준비가 되지 않음");
        }
    }

    private Order completeOrder(PaymentConfirmResponseDto responseDto) {
        UUID orderId = UUID.fromString(responseDto.getOrderId());
        Order order = orderRepository.findById(orderId).orElseThrow();

        order.completeOrder();
        order.setPaymentKey(responseDto.getPaymentKey());
        orderRepository.update(order);

        return order;
    }

    private void savePaymentAndLedger(PaymentConfirmResponseDto responseDto) {
        paymentLedgerRepository.save(responseDto.toPaymentLedgerDomain());

        // TODO - Mapper로 분리.
        Payment payment = Payment.builder()
                .paymentKey(responseDto.getPaymentKey())
                .paymentMethod(PaymentMethod.fromMethodName(responseDto.getMethod()))
                .paymentStatus(PaymentStatus.from(responseDto.getStatus()))
                .totalAmount(responseDto.getTotalAmount())
                .canceledAmount(0)
                .build();

        paymentRepository.save(payment);
    }

    private void decreaseProductStock(List<OrderItem> items) {
        items.forEach(orderItem -> {
            Long productId = orderItem.getProductId();
            Product product = productRepository.findById(productId).orElseThrow();

            product.decreaseStock(orderItem.getQuantity());
            productRepository.update(product);
        });
    }

    private void increaseProductStock(List<OrderItem> items) {
        items.forEach(orderItem -> {
            Product product = productRepository.findById(orderItem.getProductId()).orElseThrow();
            product.decreaseStock(orderItem.getQuantity());
            productRepository.update(product);
        });
    }

    private boolean isCancellable(Order order, int cancellationAmount, PaymentLedger paymentLedger) {
        return order.getOrderStatus() == OrderStatus.PURCHASE_COMPLETED &&
                paymentLedger.isCancellable(cancellationAmount);
    }

    private void updatePaymentStatus(Payment payment, Long[] cancelIds, int totalSize) {
        if(cancelIds == null || cancelIds.length == 0 || cancelIds.length == totalSize)
            payment.setPaymentStatus(PaymentStatus.CANCELED);
        else
            payment.setPaymentStatus(PaymentStatus.PARTIAL_CANCELED);
    }

    private void cancelOrderItems(Order order, Long[] itemIds) {
        if(itemIds == null || itemIds.length == 0)
            order.orderAllCancel();
        else
            order.orderCancel(itemIds);
    }
}
