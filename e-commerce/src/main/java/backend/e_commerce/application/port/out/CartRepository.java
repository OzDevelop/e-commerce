package backend.e_commerce.application.port.out;

import backend.e_commerce.domain.cart.Cart;

public interface CartRepository {
    Cart save(Cart cart);
    Cart findByUserId(Long userId);
}
