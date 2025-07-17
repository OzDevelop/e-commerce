package backend.e_commerce.application.port.in.user;

import backend.e_commerce.domain.user.User;
import backend.e_commerce.application.command.user.RegisterUserCommand;
import backend.e_commerce.application.command.user.UpdateUserCommand;

public interface UserInfoCommandUserUseCase {
    User registerUser(RegisterUserCommand command);
    User changePassword(Long userId, String newPassword);
    User updateProfile(Long userId, UpdateUserCommand command);
}
