package backend.e_commerce.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class Address {
    private final String address;
    private final String addressDetail;
    private final String zipCode;

    private final boolean isDefault;

    public static Address addAddress(String address, String addressDetail, String zipCode, boolean isDefault) {
        return Address.builder()
                .address(address)
                .addressDetail(addressDetail)
                .zipCode(zipCode)
                .isDefault(isDefault)
                .build();
    }
}
