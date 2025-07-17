package backend.e_commerce.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Address {
    private final Long id;
    private final Long userId;

    private final String address;
    private final String addressDetail;
    private final String zipCode;

    private final boolean isDefault;

    public Address(String address, String addressDetail, String zipCode, boolean isDefault) {
        this.id = null;
        this.userId = null;
        this.address = address;
        this.addressDetail = addressDetail;
        this.zipCode = zipCode;
        this.isDefault = isDefault;
    }
}
