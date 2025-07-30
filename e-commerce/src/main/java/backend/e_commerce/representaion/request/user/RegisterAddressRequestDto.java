package backend.e_commerce.representaion.request.user;

import backend.e_commerce.application.command.user.RegisterAddressCommand;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RegisterAddressRequestDto {

    private Long userId;
    private String address;
    private String addressDetail;
    private String zipCode;

    private boolean isDefault;

    public RegisterAddressCommand toRegisterAddressCommand() {
        return RegisterAddressCommand.builder()
                .userId(userId)
                .address(address)
                .addressDetail(addressDetail)
                .zipCode(zipCode)
                .isDefault(isDefault)
                .build();
    }
}
