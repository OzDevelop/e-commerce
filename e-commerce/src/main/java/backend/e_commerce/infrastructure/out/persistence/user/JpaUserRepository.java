package backend.e_commerce.infrastructure.out.persistence.user;

import backend.e_commerce.infrastructure.out.persistence.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

//TODO - Entity 생성 후 작성
public interface JpaUserRepository extends JpaRepository<UserEntity, Long> {
}
