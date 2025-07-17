package backend.e_commerce.application.command.user;

import backend.e_commerce.domain.user.UserRole;

public record RegisterUserCommand(String email, String password, String name, String phone, UserRole role) {
}
