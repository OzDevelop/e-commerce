package backend.e_commerce.infrastructure.out.persistence.cart;

import backend.e_commerce.domain.cart.Cart;
import backend.e_commerce.domain.cart.CartItem;
import backend.e_commerce.infrastructure.out.persistence.cart.entity.CartEntity;
import backend.e_commerce.infrastructure.out.persistence.cart.entity.CartItemEntity;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CartEntityMapper {
    public static CartEntity fromCartToCartEntity(Cart cart) {
        CartEntity entity = CartEntity.builder()
                .id(cart.getId())
                .userId(cart.getUserId())
                .build();

        cart.getItems().forEach(item -> {
            CartItemEntity itemEntity = CartItemEntity.builder()
                    .id(item.getId())
                    .productId(item.getProductId())
                    .quantity(item.getQuantity())
                    .cart(entity)
                    .build();
            entity.getItems().add(itemEntity);
        });
        return entity;
    }

    public static Cart fromCartEntityToCart(CartEntity entity) {
        List<CartItem> items = entity.getItems().stream()
                        .map(item -> CartItem.builder()
                                .id(item.getId())
                                .productId(item.getProductId())
                                .quantity(item.getQuantity())
                                .build())
                        .collect(Collectors.toList());

        return Cart.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .items(items)
                .build();
    }
}
