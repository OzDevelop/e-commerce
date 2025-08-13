package backend.e_commerce.representaion.in.web;

import backend.e_commerce.application.port.in.order.CheckoutCommandUseCase;
import backend.e_commerce.domain.order.Order;
import backend.e_commerce.representaion.request.chekcout.CheckoutAllRequestDto;
import backend.e_commerce.representaion.request.chekcout.CheckoutSelectedRequestDto;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
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
    public ResponseEntity<Order> checkout(@RequestBody CheckoutAllRequestDto request) {

        Order order = checkoutCommandUseCase.checkoutAll(request.getUserId(), request.getAddressId());

        return ResponseEntity.ok(order);
    }

    @PostMapping("/selected")
    public ResponseEntity<Order> checkoutSelected(
            @RequestBody CheckoutSelectedRequestDto request
    ) {
        Order order = checkoutCommandUseCase.checkoutSelected(
                request.getUserId(),
                request.getAddressId(),
                request.getSelectedItemIds()
        );
        return ResponseEntity.ok(order);
    }
}
