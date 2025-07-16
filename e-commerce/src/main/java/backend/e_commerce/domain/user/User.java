package backend.e_commerce.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class User {
    private final Long id;

    private final String email;
    private String password;

    private final String name;
    private final String phone;
    private final UserRole role;

    public static User createUser(String email, String password, String name, String phone, UserRole role) {
        return new User(null, email, password, name, phone, role);
    }

    public User changePassword(String newPassword) {
        return new User(this.id, this.email, newPassword, this.name, this.phone, this.role);
    }

    public User changeUserInfo(String newName, String newPhone) {
        return new User(this.id, this.email, this.password, newName, newPhone, this.role);
    }
}
