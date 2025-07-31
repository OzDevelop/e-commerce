package backend.e_commerce.infrastructure.out.persistence.user.entity;

import backend.core.common.entity.TimeBaseEntity;
import backend.e_commerce.domain.user.Address;
import backend.e_commerce.domain.user.User;
import backend.e_commerce.domain.user.UserRole;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserEntity extends TimeBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;
    private String name;
    private String phone;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AddressEntity> addresses = new ArrayList<>();

    public static UserEntity create(User user) {
        UserEntity userEntity = new UserEntity(
                null,
                user.getEmail(),
                user.getPassword(),
                user.getName(),
                user.getPhone(),
                user.getRole(),
                new ArrayList<>()
        );

        for (Address address : user.getAddresses()) {
            AddressEntity addressEntity = AddressEntity.create(address);
            userEntity.addAddress(addressEntity);
        }

        return userEntity;
    }

    public void addAddress(AddressEntity address) {
        addresses.add(address);
        address.setUser(this);
    }

    public void removeAddress(Long addressId) {
        AddressEntity toRemove = this.addresses.stream()
                .filter(addr -> addr.getId().equals(addressId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("주소를 찾을 수 없습니다."));

        this.addresses.remove(toRemove);
        toRemove.setUser(null); // 관계 제거
    }

    public User toDomain() {
        List<Address> addressList = this.addresses.stream()
                .map(AddressEntity::toDomain)
                .toList();

        return User.builder()
                .id(id)
                .email(email)
                .password(password)
                .name(name)
                .phone(phone)
                .role(role)
                .addresses(addressList)
                .build();
    }
}

