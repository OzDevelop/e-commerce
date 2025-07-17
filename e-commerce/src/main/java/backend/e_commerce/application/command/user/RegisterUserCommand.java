package backend.e_commerce.application.command.user;

import backend.e_commerce.domain.user.Address;
import backend.e_commerce.domain.user.User;
import backend.e_commerce.domain.user.UserRole;
import java.util.List;

public record RegisterUserCommand(
        String email,
        String password,
        String name,
        String phone,
        UserRole role,
        List<AddressCommand> address
) {
    public List<Address> toAddressDomainList() {
        return address.stream()
                .map(AddressCommand::toDomain)
                .toList();
    }

    public User toDomain() {
        List<Address> addresses = this.toAddressDomainList();
        return User.createUser(email, password, name, phone, role, addresses);
    }

    public record AddressCommand(String address, String addressDetail, String zipcode, boolean isDefault) {
        public Address toDomain() {
            return new Address(address, addressDetail, zipcode, isDefault);
        }
    }
}
