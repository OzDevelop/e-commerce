package backend.e_commerce.infrastructure.out.persistence.user;

import backend.e_commerce.application.port.out.UserRepository;
import backend.e_commerce.domain.user.User;
import backend.e_commerce.infrastructure.out.persistence.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

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
                .orElseThrow(IllegalArgumentException::new);
        return userEntity.toDomain();
    }
}
