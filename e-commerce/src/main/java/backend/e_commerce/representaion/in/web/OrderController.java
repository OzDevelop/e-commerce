package backend.e_commerce.representaion.in.web;

import backend.e_commerce.application.command.order.CreateOrderCommand;
import backend.e_commerce.application.port.in.order.OrderCommandUseCase;
import backend.e_commerce.domain.order.Order;
import backend.e_commerce.domain.order.OrderItem;
import backend.e_commerce.representaion.request.CreateOrderRequestDto;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderCommandUseCase orderCommandUseCase;

    @PostMapping
    public ResponseEntity<Order>  createOrder(@RequestBody CreateOrderRequestDto requestDto) {
        CreateOrderCommand command = CreateOrderCommand.toCommand(requestDto);

        Order createdOrder = orderCommandUseCase.createOrder(command);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }

    @PatchMapping("/{orderId}/complete")
    public ResponseEntity<Void>  completeOrder(@PathVariable UUID orderId) {
        orderCommandUseCase.complete(orderId);

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{orderId}/items/{orderItemId}/cancel")
    public ResponseEntity<Void> cancelOrderItem(@PathVariable UUID orderId,
                                                @PathVariable Long[] orderItemId) {
        orderCommandUseCase.cancelItem(orderId, orderItemId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{orderId}/cancel")
    public ResponseEntity<Void>  cancelOrder(@PathVariable UUID orderId) {
        orderCommandUseCase.cancelAll(orderId);
        return ResponseEntity.ok().build();
    }
}
