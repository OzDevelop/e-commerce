package backend.e_commerce.application.service;

import backend.e_commerce.application.command.cart.AddItemCommand;
import backend.e_commerce.application.command.cart.ChangeQuantityCommand;
import backend.e_commerce.application.command.cart.DeleteItemCommand;
import backend.e_commerce.application.port.in.cart.CartCommandUseCase;
import backend.e_commerce.application.port.out.CartRepository;
import backend.e_commerce.domain.cart.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService implements CartCommandUseCase {
    private final CartRepository cartRepository;

    @Override
    public Cart createCart(Long userId) {
        Cart cart = Cart.create(userId);
        return cartRepository.save(cart);
    }

    @Override
    public Cart addItem(AddItemCommand command) {
        Cart cart = cartRepository.findByUserId(command.getUserId());

        cart.addItem(command.getProductId(), command.getQuantity());

        return cartRepository.save(cart);
    }

    @Override
    public Cart changeQuantity(ChangeQuantityCommand command) {
        Cart cart = cartRepository.findByUserId(command.getUserId());

        cart.changeQuantity(command.getCartItemId(), command.getQuantity());

        return cartRepository.save(cart);
    }

    @Override
    public Cart deleteItem(DeleteItemCommand command) {
        Cart cart = cartRepository.findByUserId(command.getUserId());
        cart.deleteItem(command.getCartItemId());
        return cartRepository.save(cart);
    }

    @Override
    public Cart deleteAllItems(Long userId) {
        Cart cart = cartRepository.findByUserId(userId);
        cart.clear();
        return cartRepository.save(cart);
    }
}
