package backend.e_commerce.infrastructure.out.persistence.cart;

import backend.e_commerce.application.port.out.CartRepository;
import backend.e_commerce.domain.cart.Cart;
import backend.e_commerce.infrastructure.out.persistence.cart.entity.CartEntity;
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
}
