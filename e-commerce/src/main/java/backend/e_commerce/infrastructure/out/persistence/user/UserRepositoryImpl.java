package backend.e_commerce.infrastructure.out.persistence.user;

import backend.e_commerce.application.port.out.UserRepository;
import backend.e_commerce.domain.user.Address;
import backend.e_commerce.domain.user.User;
import backend.e_commerce.infrastructure.out.persistence.user.entity.AddressEntity;
import backend.e_commerce.infrastructure.out.persistence.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final JpaUserRepository jpaUserRepository;

    @Override
    public User save(User user) {
        System.out.println(user.getPassword());
        UserEntity userEntity =  UserEntity.create(user);

        System.out.println("userEntity.getPassword()"+userEntity.getPassword());

        userEntity = jpaUserRepository.save(userEntity);
        return userEntity.toDomain();
    }

    @Override
    public User findById(Long userId) {
        UserEntity userEntity = jpaUserRepository
                .findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id " + id));
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
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        AddressEntity addressEntity = AddressEntity.create(address);
        userEntity.addAddress(addressEntity);
    }

    @Override
    public void removeAddress(Long userId, Long addressId) {
        UserEntity userEntity = jpaUserRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with id " + userId + " not found"));

        userEntity.removeAddress(addressId);
    }

}
