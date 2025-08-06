package backend.e_commerce.infrastructure.out.persistence.delivery.entity;

import backend.e_commerce.domain.user.Address;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class EmbeddedAddress {
    private String address;
    private String addressDetail;
    private String zipCode;
    private boolean isDefault;
    
    public static EmbeddedAddress from(Address address) {
        return EmbeddedAddress.builder()
                .address(address.getAddress())
                .addressDetail(address.getAddressDetail())
                .zipCode(address.getZipCode())
                .build();
    }
}
