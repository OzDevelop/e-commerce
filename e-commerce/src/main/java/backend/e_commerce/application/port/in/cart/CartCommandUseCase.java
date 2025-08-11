package backend.e_commerce.application.port.in.cart;

import backend.e_commerce.application.command.cart.AddItemCommand;
import backend.e_commerce.application.command.cart.ChangeQuantityCommand;
import backend.e_commerce.application.command.cart.DeleteItemCommand;
import backend.e_commerce.domain.cart.Cart;

public interface CartCommandUseCase {
    Cart createCart(Long userId);
    Cart addItem(AddItemCommand command);
    Cart changeQuantity(ChangeQuantityCommand command);
    Cart deleteItem(DeleteItemCommand command);
    Cart deleteAllItems(Long userId);

}
