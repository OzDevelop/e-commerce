package backend.e_commerce.infrastructure.out.persistence.cart;

import backend.e_commerce.application.port.out.CartRepository;
import backend.e_commerce.domain.cart.Cart;
import backend.e_commerce.infrastructure.out.persistence.cart.entity.CartEntity;
import org.springframework.stereotype.Repository;

@Repository
public class CartRepositoryImpl implements CartRepository {
    @Override
    public Cart save(Cart cart) {

        return null;
    }

    @Override
    public Cart findByUserId(Long userId) {
        return null;
    }
}
