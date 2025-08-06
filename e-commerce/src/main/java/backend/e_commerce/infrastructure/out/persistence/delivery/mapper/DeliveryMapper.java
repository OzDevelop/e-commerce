package backend.e_commerce.infrastructure.out.persistence.delivery.mapper;

import backend.e_commerce.application.command.delivery.CreateDeliveryCommand;
import backend.e_commerce.application.command.delivery.StartShippingCommand;
import backend.e_commerce.domain.delivery.Delivery;
import backend.e_commerce.domain.delivery.DeliveryStatus;
import backend.e_commerce.domain.user.Address;
import backend.e_commerce.infrastructure.out.persistence.delivery.entity.DeliveryEntity;
import backend.e_commerce.infrastructure.out.persistence.delivery.entity.EmbeddedAddress;
import backend.e_commerce.infrastructure.out.persistence.order.entity.OrderEntity;
import backend.e_commerce.infrastructure.out.persistence.order.mapper.OrderEntityMapper;
import backend.e_commerce.infrastructure.out.persistence.user.AddressEntityMapper;
import backend.e_commerce.infrastructure.out.persistence.user.entity.AddressEntity;
import backend.e_commerce.representaion.request.delivery.CreateDeliveryRequestDto;
import backend.e_commerce.representaion.request.delivery.StartShippingRequestDto;
import java.util.List;
import java.util.stream.Collectors;

public class DeliveryMapper {
    public static Delivery fromEntityToDomain(DeliveryEntity entity) {
        EmbeddedAddress embeddedAddress = entity.getAddress();

        Address address = new Address(
                embeddedAddress.getAddress(),
                embeddedAddress.getAddressDetail(),
                embeddedAddress.getZipCode(),
                embeddedAddress.isDefault()
        );

        return Delivery.builder()
                .id(entity.getId())
                .orderId(entity.getOrder().getId())
                .status(entity.getStatus())
                .trackingNumber(entity.getTrackingNumber())
                .deliveryCompany(entity.getDeliveryCompany())
                .address(address)
                .receiverName(entity.getReceiverName())
                .receiverPhone(entity.getReceiverPhone())
                .shippedAt(entity.getShippedAt())
                .deliveredAt(entity.getDeliveredAt())
                .build();
    }

    public static List<Delivery> fromEntityListToDomainList(List<DeliveryEntity> entities) {
        return entities.stream()
                .map(DeliveryMapper::fromEntityToDomain)
                .collect(Collectors.toList());
    }

    public static DeliveryEntity fromDomainToEntity(Delivery domain, OrderEntity orderEntity) {
        Address address = domain.getAddress();

        EmbeddedAddress embeddedAddress = EmbeddedAddress.builder()
                .address(address.getAddress())
                .addressDetail(address.getAddressDetail())
                .zipCode(address.getZipCode())
                .isDefault(address.isDefault())
                .build();

        return DeliveryEntity.builder()
                .order(orderEntity)
                .status(domain.getStatus())
                .trackingNumber(domain.getTrackingNumber())
                .deliveryCompany(domain.getDeliveryCompany())
                .address(embeddedAddress)
                .receiverName(domain.getReceiverName())
                .receiverPhone(domain.getReceiverPhone())
                .shippedAt(domain.getShippedAt())
                .deliveredAt(domain.getDeliveredAt())
                .build();
    }


    public static DeliveryEntity toEntity(Delivery delivery, DeliveryEntity entity) {
        Address address = delivery.getAddress();

        EmbeddedAddress embeddedAddress = EmbeddedAddress.builder()
                .address(address.getAddress())
                .addressDetail(address.getAddressDetail())
                .zipCode(address.getZipCode())
                .isDefault(address.isDefault())
                .build();


        entity.setStatus(delivery.getStatus());
        entity.setTrackingNumber(delivery.getTrackingNumber());
        entity.setDeliveryCompany(delivery.getDeliveryCompany());
        entity.setShippedAt(delivery.getShippedAt());
        entity.setDeliveredAt(delivery.getDeliveredAt());
        entity.setAddress(embeddedAddress);
        return entity;
    }

    public static CreateDeliveryCommand fromCreateDtoToCommand(CreateDeliveryRequestDto dto) {
        return CreateDeliveryCommand.builder()
                .orderId(dto.getOrderId())
                .addressId(dto.getAddressId())
                .receiverName(dto.getReceiverName())
                .receiverPhone(dto.getReceiverPhone())
                .build();
    }

    public static StartShippingCommand fromShippingDtoToCommand(StartShippingRequestDto dto) {
        return StartShippingCommand.builder()
                .deliveryId(dto.getDeliveryId())
                .trackingNumber(dto.getTrackingNumber())
                .deliveryCompany(dto.getDeliveryCompany())
//                .shippedAt(dto.getShippedAt())
                .build();
    }
}

