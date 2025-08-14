package backend.e_commerce.representaion.in.web;

import backend.e_commerce.application.command.delivery.StartShippingCommand;
import backend.e_commerce.application.port.in.Delivery.DeliveryCommandUseCase;
import backend.e_commerce.application.port.in.Delivery.DeliveryQueryUseCase;
import backend.e_commerce.domain.delivery.Delivery;
import backend.e_commerce.infrastructure.out.persistence.delivery.mapper.DeliveryMapper;
import backend.e_commerce.representaion.request.delivery.ChangeAddressRequest;
import backend.e_commerce.representaion.request.delivery.CreateDeliveryRequest;
import backend.e_commerce.representaion.request.delivery.StartShippingRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(("/api/delivery"))
@RequiredArgsConstructor
@Slf4j
public class DeliveryController {
    private final DeliveryCommandUseCase deliveryCommandUseCase;
    private final DeliveryQueryUseCase deliveryQueryUseCase;

    @PostMapping("/create")
    public ResponseEntity<Delivery> addDelivery(@RequestBody CreateDeliveryRequest dto) {
        Delivery delivery = deliveryCommandUseCase.createDelivery(DeliveryMapper.fromCreateDtoToCommand(dto));

        return ResponseEntity.ok(delivery);
    }

    @PostMapping("/start-shipping")
    public ResponseEntity<Delivery> startShipping(@RequestBody StartShippingRequest dto) {

        System.out.println("dto.getId >>" + dto.getDeliveryId() );


        StartShippingCommand command = DeliveryMapper.fromShippingDtoToCommand(dto);

        System.out.println("command.getId >>" + command.getDeliveryId() );

        Delivery delivery = deliveryCommandUseCase.startShipping(command);

        return ResponseEntity.ok(delivery);
    }

    @PostMapping("/{deliveryId}/complete")
    public ResponseEntity<Delivery> completeDelivery(@PathVariable Long deliveryId) {
        Delivery delivery = deliveryCommandUseCase.completeDelivery(deliveryId);

        return ResponseEntity.ok(delivery);
    }

    @PostMapping("/change-address")
    public ResponseEntity<Delivery> changeAddress(@RequestBody ChangeAddressRequest dto) {
        Delivery delivery = deliveryCommandUseCase.changeAddress(dto.getDeliveryId(), dto.getAddressId());

        return ResponseEntity.ok(delivery);
    }

//    @GetMapping("/order/{orderId}")
//    public List<Delivery> getDeliveryByOrderId(@PathVariable UUID orderId) {
//        return deliveryQueryUseCase.getDeliveryByOrderId(orderId);
//    }
}
