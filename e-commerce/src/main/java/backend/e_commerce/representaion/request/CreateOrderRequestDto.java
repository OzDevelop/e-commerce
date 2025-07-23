package backend.e_commerce.representaion.request;

import backend.e_commerce.domain.payment.Payment;
import backend.e_commerce.domain.user.Address;
import java.util.List;
import lombok.Getter;

@Getter
public class CreateOrderRequestDto {
    private Long userId;
    private Address address;
    private List<OrderItemRequestDto> orderItems;
    private Payment payment;

    @Getter
    public static class OrderItemRequestDto {
        private String productId;
        private int quantity;
        private int unitPrice;
    }
}
