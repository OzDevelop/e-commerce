package backend.e_commerce.application.command.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChangePasswordCommand {
    private final Long userId;
    private final String oldPassword;
    private final String newPassword;

}
