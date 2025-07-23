package backend.e_commerce.infrastructure.out.persistence.user;

import backend.e_commerce.domain.user.Address;
import backend.e_commerce.infrastructure.out.persistence.user.entity.AddressEntity;

public class AddressEntityMapper {
       // Address
    public static Address toDomain(AddressEntity entity) {
        return new Address(
                entity.getAddress(),
                entity.getAddressDetail(),
                entity.getZipCode(),
                entity.isDefault()
        );
    }

    public static AddressEntity toEntity(Address address) {
        return AddressEntity.builder()
                .address(address.getAddress())
                .addressDetail(address.getAddressDetail())
                .zipCode(address.getZipCode())
                .isDefault(address.isDefault())
                .build();
    }
}
