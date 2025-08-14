package backend.e_commerce.representaion.in.web;

import backend.e_commerce.application.port.in.order.CheckoutCommandUseCase;
import backend.e_commerce.domain.order.Order;
import backend.e_commerce.representaion.request.chekcout.CheckoutAllRequest;
import backend.e_commerce.representaion.request.chekcout.CheckoutSelectedRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/checkout")
public class CheckoutController {
    private final CheckoutCommandUseCase checkoutCommandUseCase;

    @PostMapping("/all")
    public ResponseEntity<Order> checkout(@RequestBody CheckoutAllRequest request) {

        Order order = checkoutCommandUseCase.checkoutAll(request.getUserId(), request.getAddressId());

        return ResponseEntity.ok(order);
    }

    @PostMapping("/selected")
    public ResponseEntity<Order> checkoutSelected(
            @RequestBody CheckoutSelectedRequest request
    ) {
        Order order = checkoutCommandUseCase.checkoutSelected(
                request.getUserId(),
                request.getAddressId(),
                request.getSelectedItemIds()
        );
        return ResponseEntity.ok(order);
    }
}
