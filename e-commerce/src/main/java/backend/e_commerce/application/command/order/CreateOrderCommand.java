package backend.e_commerce.application.command.order;

import backend.e_commerce.domain.order.OrderItem;
import backend.e_commerce.domain.payment.Payment;
import backend.e_commerce.domain.user.Address;
import backend.e_commerce.representaion.request.order.CreateOrderRequestDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CreateOrderCommand {
    private final Long userId;
    private final Address address;
    private final List<OrderItem> orderItems;
    private final Payment payment;

    public static CreateOrderCommand toCommand(CreateOrderRequestDto dto) {
        List<OrderItem> orderItems = dto.getOrderItems().stream()
                .map(item -> OrderItem.builder()
                        .productId(item.getProductId())  // productId 타입 맞추기 주의 (String or Long)
                        .quantity(item.getQuantity())
                        .amount(item.getUnitPrice())
                        .build())
                .toList();

        return CreateOrderCommand.builder()
                .userId(dto.getUserId())
                .address(dto.getAddress())
                .orderItems(orderItems)
                .build();
    }
}
