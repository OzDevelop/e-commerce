package backend.e_commerce.infrastructure.out.persistence.user;

import backend.e_commerce.domain.user.Address;
import backend.e_commerce.infrastructure.out.persistence.user.entity.AddressEntity;
import backend.e_commerce.representaion.request.delivery.ChangeAddressRequestDto;

public class AddressEntityMapper {
    public static Address fromEntityToDomain(AddressEntity entity) {
        return new Address(
                entity.getAddress(),
                entity.getAddressDetail(),
                entity.getZipCode(),
                entity.isDefault()
        );
    }

    public static AddressEntity fromDomainToEntity(Address address) {
        return AddressEntity.builder()
                .address(address.getAddress())
                .addressDetail(address.getAddressDetail())
                .zipCode(address.getZipCode())
                .isDefault(address.isDefault())
                .build();
    }

//    public static Address fromChangeDtoToDomain(ChangeAddressRequestDto dto) {
//        return Address.builder()
//                .address(dto.)
//                .build();
//    }
}
