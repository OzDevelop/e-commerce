package backend.e_commerce.representaion.request.user;

import backend.e_commerce.application.command.user.RegisterUserCommand;
import backend.e_commerce.application.command.user.RegisterUserCommand.AddressCommand;
import backend.e_commerce.domain.user.UserRole;
import java.util.List;

public record RegisterUserRequestDto (
    String email,
    String password,
    String name,
    String phone,
    String role,
    List<AddressRequestDto> addresses
) {
    public RegisterUserCommand toRegisterUserCommand() {
        List<AddressCommand> addressCommands = addresses.stream()
                .map(AddressRequestDto::toCommand)
                .toList();

        return new RegisterUserCommand(
                email,
                password,
                name,
                phone,
                UserRole.valueOf(role.toUpperCase()),
                addressCommands

        );
    }
}
