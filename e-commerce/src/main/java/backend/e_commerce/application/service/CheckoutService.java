package backend.e_commerce.application.service;

import backend.e_commerce.application.command.order.CreateOrderCommand;
import backend.e_commerce.application.port.in.order.CheckoutCommandUseCase;
import backend.e_commerce.application.port.in.order.OrderCommandUseCase;
import backend.e_commerce.application.port.out.CartRepository;
import backend.e_commerce.application.port.out.OrderRepository;
import backend.e_commerce.application.port.out.ProductRepository;
import backend.e_commerce.domain.cart.Cart;
import backend.e_commerce.domain.cart.CartItem;
import backend.e_commerce.domain.order.Order;
import backend.e_commerce.domain.order.OrderItem;
import backend.e_commerce.domain.product.Product;
import backend.e_commerce.domain.user.Address;
import backend.e_commerce.infrastructure.out.persistence.user.AddressEntityMapper;
import backend.e_commerce.infrastructure.out.persistence.user.JpaAddressRepository;
import backend.e_commerce.infrastructure.out.persistence.user.entity.AddressEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CheckoutService implements CheckoutCommandUseCase {
    private final CartRepository cartRepository;
    private final JpaAddressRepository jpaAddressRepository;

    private final OrderCommandUseCase orderCommandUseCase;

    @Override
    @Transactional
    public Order checkoutAll(Long userId, Long addressId) {
        Cart cart = cartRepository.findByUserId(userId);

        return checkoutInternal(userId, addressId, cart.getItems());
    }

    @Override
    @Transactional
    public Order checkoutSelected(Long userId, Long addressId, List<Long> selectedItemIds) {
        Cart cart = cartRepository.findByUserId(userId);
        List<CartItem> selectedItems = cart.getItems().stream()
                .filter(item -> selectedItemIds.contains(item.getId()))
                .toList();

        return checkoutInternal(userId, addressId, selectedItems);
    }

    private Order checkoutInternal(Long userId, Long addressId, List<CartItem> items) {
        if(items.isEmpty()) {
            // TODO: 예외 처리 - 선택된 상품 없음
        }

        // 1. cartItem -> orderItem 변환
        List<OrderItem> orderItems = items.stream()
                .map(this::mapCartItemToOrderItem)
                .toList();

        // 2. 주소록에서 주소 조회 & 유저 검증
        AddressEntity addressEntity = jpaAddressRepository.findById(addressId)
                .filter(a -> a.getUser().getId().equals(userId))
                .orElseThrow(IllegalArgumentException::new);

        Address address = AddressEntityMapper.fromEntityToDomain(addressEntity);

        // 3. 스냅샷 주소 생성
        Address shippingAddress = new Address(
                address.getAddress(),
                address.getAddressDetail(),
                address.getZipCode(),
                false
        );

        // 4. CreateOrderCommand 생성
        CreateOrderCommand command = CreateOrderCommand.builder()
                .userId(userId)
                .address(address)
                .orderItems(orderItems)
                .build();

        // 5. createOrder
        Order order = orderCommandUseCase.createOrder(command);

        // 6. cart에서 물건 삭제
        cartRepository.removeItems(userId, items);

        return order;
    }

    private OrderItem mapCartItemToOrderItem(CartItem cartItem) {
        return OrderItem.builder()
                .productId(cartItem.getProductId())
                .quantity(cartItem.getQuantity())
                // 가격은 OrderService에서 ProductRepository로 다시 조회 후 세팅
                .build();
    }
}
