package backend.e_commerce.application.port.in.order;

import backend.e_commerce.domain.order.Order;
import backend.e_commerce.domain.user.Address;
import java.util.List;

public interface CheckoutCommandUseCase {
    Order checkoutAll(Long userId, Long addressId);
    Order checkoutSelected(Long userId, Long addressId, List<Long> selectedItemIds);
}
