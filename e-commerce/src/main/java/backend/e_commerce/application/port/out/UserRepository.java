package backend.e_commerce.application.port.out;

import backend.e_commerce.domain.user.Address;
import backend.e_commerce.domain.user.User;


public interface UserRepository {
    User save(User user);
    User findById(Long userId);

    void addAddress(Long userId, Address address);
    void removeAddress(Long userId, Long addressId);

}
