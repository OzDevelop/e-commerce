package backend.e_commerce.infrastructure.out.persistence.user.entity;

import backend.e_commerce.application.command.user.RegisterUserCommand.AddressCommand;
import backend.e_commerce.domain.user.Address;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "addresses")
@AllArgsConstructor
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address;
    private String addressDetail;
    private String zipCode;

    private boolean isDefault;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public static AddressEntity create(Address address) {
        AddressEntity addressEntity = new AddressEntity(
                null,
                address.getAddress(),
                address.getAddressDetail(),
                address.getZipCode(),
                address.isDefault(),
                null
        );
        return addressEntity;
    }

    public Address toDomain() {
        return new Address(address, addressDetail, zipCode, isDefault);
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
