package backend.e_commerce.infrastructure.out.persistence.user;

import backend.e_commerce.infrastructure.out.persistence.user.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaAddressRepository extends JpaRepository<AddressEntity, Long> {
}
