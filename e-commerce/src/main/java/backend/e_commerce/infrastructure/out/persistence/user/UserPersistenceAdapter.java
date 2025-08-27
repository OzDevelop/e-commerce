package backend.e_commerce.infrastructure.out.persistence.user;

import backend.core.common.errorcode.errorcode.UserErrorCode;
import backend.core.common.errorcode.execption.UserException;
import backend.e_commerce.application.port.out.UserPersistencePort;
import backend.e_commerce.domain.user.Address;
import backend.e_commerce.domain.user.User;
import backend.e_commerce.infrastructure.out.persistence.user.entity.AddressEntity;
import backend.e_commerce.infrastructure.out.persistence.user.entity.UserEntity;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserPersistencePort {

    private final JpaUserRepository jpaUserRepository;

    @Override
    public User save(User user) {
        UserEntity userEntity =  UserEntity.create(user);

        userEntity = jpaUserRepository.save(userEntity);
        return userEntity.toDomain();
    }

    @Override
    public User findById(Long userId) {
        UserEntity userEntity = jpaUserRepository
                .findById(userId)
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND, Map.of("userId", userId)));
        return userEntity.toDomain();
    }

    @Override
    public User findByEmail(String email) {
        UserEntity userEntity = jpaUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        return userEntity.toDomain();
    }

    @Override
    public void addAddress(Long userId, Address address) {
        UserEntity userEntity = jpaUserRepository.findById(userId)
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND, Map.of("userId", userId)));

        AddressEntity addressEntity = AddressEntity.create(address);
        userEntity.addAddress(addressEntity);
    }

    @Override
    public void removeAddress(Long userId, Long addressId) {
        UserEntity userEntity = jpaUserRepository.findById(userId)
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND, Map.of("userId", userId)));

        userEntity.removeAddress(addressId);
    }
}
