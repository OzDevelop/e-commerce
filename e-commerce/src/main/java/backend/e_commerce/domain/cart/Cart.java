package backend.e_commerce.domain.cart;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class Cart {
    private final Long id;
    private final Long userId;
    private final List<CartItem> items;

    // cart는 사용자 생성 시 항상 존재해야 함.
    // item 추가(개수 중가), 삭제, 주문

    // cart 생성(registerUser 시 생성됨)
    public static Cart create(Long userId) {
        return Cart.builder()
                .userId(userId)
                .items(new ArrayList<>())
                .build();
    }

    public void addItem(Long productId, int quantity) {
        Optional<CartItem> existingItem = items.stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.changeQuantity(quantity);
        } else {
            CartItem newItem = CartItem.builder()
                    .id(null)
                    .productId(productId)
                    .quantity(quantity)
                    .build();
            items.add(newItem);
        }
    }

    public void changeQuantity(Long cartItemId, int quantity) {
        CartItem item = findCartItemById(cartItemId);
        if (item == null) {
            throw new IllegalArgumentException("Cart item not found for id: " + cartItemId);
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        } else {
            item.changeQuantity(quantity);
        }
    }

    public void deleteItem(Long cartItemId) {
        Iterator<CartItem> iterator = items.iterator();
        boolean removed = false;
        while (iterator.hasNext()) {
            CartItem item = iterator.next();
            if (item.getId().equals(cartItemId)) {
                iterator.remove();
                removed = true;
                break;
            }
        }
        if (!removed) {
            throw new IllegalArgumentException("Cart item not found: " + cartItemId);
        }
    }

    public void clear() {
        items.clear();
    }

    private CartItem findCartItemById(Long cartItemId) {
        return items.stream()
                .filter(item -> item.getId().equals(cartItemId))
                .findFirst()
                .orElse(null);
    }
}
