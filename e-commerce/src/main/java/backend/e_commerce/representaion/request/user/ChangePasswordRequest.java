package backend.e_commerce.representaion.request.user;

import backend.e_commerce.application.command.user.ChangePasswordCommand;

public record ChangePasswordRequest(
    Long userId,
    String oldPassword,
    String newPassword
) {

    public ChangePasswordCommand toChangePasswordCommand() {
        return new ChangePasswordCommand(userId, oldPassword, newPassword);
    }
}
