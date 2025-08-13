package backend.e_commerce.application.port.out;

import backend.e_commerce.domain.cart.Cart;
import backend.e_commerce.domain.cart.CartItem;
import java.util.List;

public interface CartRepository {
    Cart save(Cart cart);
    Cart findByUserId(Long userId);

    void removeItems(Long userId, List<CartItem> items);
}
