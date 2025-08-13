package backend.e_commerce.infrastructure.out.persistence.cart;

import backend.e_commerce.application.port.out.CartRepository;
import backend.e_commerce.domain.cart.Cart;
import backend.e_commerce.domain.cart.CartItem;
import backend.e_commerce.infrastructure.out.persistence.cart.entity.CartEntity;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CartRepositoryImpl implements CartRepository {
    private final JpaCartRepository jpaCartRepository;

    @Override
    public Cart save(Cart cart) {
        CartEntity entity = CartEntityMapper.fromCartToCartEntity(cart);
        CartEntity saved = jpaCartRepository.save(entity);
        return CartEntityMapper.fromCartEntityToCart(saved);
    }

    @Override
    public Cart findByUserId(Long userId) {
        return jpaCartRepository.findByUserId(userId)
                .map(CartEntityMapper::fromCartEntityToCart)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found for userId: " + userId));
    }

    @Override
    public void removeItems(Long userId, List<CartItem> items) {
        CartEntity cartEntity = jpaCartRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalStateException("Cart not found for userId: " + userId));

        List<Long> removeIds = items.stream().map(CartItem::getId).toList();

        cartEntity.getItems().removeIf(itemEntity -> removeIds.contains(itemEntity.getId()));
    }
}
