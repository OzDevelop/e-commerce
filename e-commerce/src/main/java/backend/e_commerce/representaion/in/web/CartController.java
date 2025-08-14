package backend.e_commerce.representaion.in.web;

import backend.e_commerce.application.command.cart.AddItemCommand;
import backend.e_commerce.application.command.cart.ChangeQuantityCommand;
import backend.e_commerce.application.command.cart.DeleteItemCommand;
import backend.e_commerce.application.port.in.cart.CartCommandUseCase;
import backend.e_commerce.domain.cart.Cart;
import backend.e_commerce.representaion.request.cart.AddItemRequest;
import backend.e_commerce.representaion.request.cart.ChangeQuantityRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/carts")
@RequiredArgsConstructor
public class CartController {
    private final CartCommandUseCase cartCommandUseCase;

    // 1. 장바구니 생성 (보통 회원가입 시 생성)
    @PostMapping("/{userId}")
    public ResponseEntity<Cart> createCart(@PathVariable Long userId) {
        Cart cart = cartCommandUseCase.createCart(userId);
        return ResponseEntity.ok(cart);
    }

    // 2. 아이템 추가
    @PostMapping("/{userId}/items")
    public ResponseEntity<Cart> addItem(@PathVariable Long userId,
                                        @RequestBody AddItemRequest dto) {

        AddItemCommand command = AddItemCommand.builder()
                .userId(userId)
                .productId(dto.getProductId())
                .quantity(dto.getQuantity())
                .build();

        Cart updatedCart = cartCommandUseCase.addItem(command);
        return ResponseEntity.ok(updatedCart);
    }

    // 3. 아이템 수량 변경
    @PutMapping("/{userId}/items/{cartItemId}")
    public ResponseEntity<Cart> changeQuantity(@PathVariable Long userId,
                                               @PathVariable Long cartItemId,
                                               @RequestBody ChangeQuantityRequest dto) {
        ChangeQuantityCommand command = ChangeQuantityCommand.builder()
                .userId(userId)
                .cartItemId(cartItemId)
                .quantity(dto.getQuantity())
                .build();

        Cart updatedCart = cartCommandUseCase.changeQuantity(command);
        return ResponseEntity.ok(updatedCart);
    }

    // 4. 아이템 삭제
    @DeleteMapping("/{userId}/items/{cartItemId}")
    public ResponseEntity<Cart> deleteItem(@PathVariable Long userId,
                                           @PathVariable Long cartItemId) {
        DeleteItemCommand cmd = DeleteItemCommand.builder()
                .userId(userId)
                .cartItemId(cartItemId)
                .build();

        Cart updatedCart = cartCommandUseCase.deleteItem(cmd);
        return ResponseEntity.ok(updatedCart);
    }

    // 5. 장바구니 전체 아이템 삭제
    @DeleteMapping("/{userId}/items")
    public ResponseEntity<Cart> deleteAllItems(@PathVariable Long userId) {
        Cart updatedCart = cartCommandUseCase.deleteAllItems(userId);
        return ResponseEntity.ok(updatedCart);
    }
}
