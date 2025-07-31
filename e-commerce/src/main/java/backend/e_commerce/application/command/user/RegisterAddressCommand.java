package backend.e_commerce.application.command.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class RegisterAddressCommand {
    private final Long userId;
    private final String address;
    private final String addressDetail;
    private final String zipCode;

    private final boolean isDefault;
}
