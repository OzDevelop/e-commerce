package backend.e_commerce.domain.user;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class User {
    private final Long id;

    private final String email;
    private final String password;

    private final String name;
    private final String phone;
    private final UserRole role;
    private final List<Address> addresses;

    public static User createUser(String email, String password, String name, String phone, UserRole role, List<Address> addresses) {
        return new User(null, email, password, name, phone, role, addresses);
    }

    public User changePassword(String newPassword) {
        return new User(this.id, this.email, newPassword, this.name, this.phone, this.role, this.addresses);
    }

    public User changeUserInfo(String newName, String newPhone) {
        return new User(this.id, this.email, this.password, newName, newPhone, this.role, this.addresses);
    }
}
