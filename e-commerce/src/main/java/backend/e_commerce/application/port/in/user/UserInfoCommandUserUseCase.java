package backend.e_commerce.application.port.in.user;

import backend.core.common.JwtToken;
import backend.e_commerce.application.command.user.ChangePasswordCommand;
import backend.e_commerce.application.command.user.RegisterAddressCommand;
import backend.e_commerce.domain.user.Address;
import backend.e_commerce.domain.user.User;
import backend.e_commerce.application.command.user.RegisterUserCommand;
import backend.e_commerce.application.command.user.UpdateUserCommand;
import java.util.List;

public interface UserInfoCommandUserUseCase {
    User registerUser(RegisterUserCommand command);
    JwtToken loginUser(String email, String password);
    User changePassword(ChangePasswordCommand command);
    User updateProfile(Long userId, UpdateUserCommand command);

    Address addAddress(RegisterAddressCommand command);
    void removeAddress(Long userId, Long addressId);
}
