package backend.e_commerce.application.service;

import backend.e_commerce.application.command.user.ChangePasswordCommand;
import backend.e_commerce.application.command.user.RegisterAddressCommand;
import backend.e_commerce.application.port.in.user.UserInfoCommandUserUseCase;
import backend.e_commerce.application.port.out.UserRepository;
import backend.e_commerce.domain.user.Address;
import backend.e_commerce.domain.user.User;
import backend.e_commerce.application.command.user.RegisterUserCommand;
import backend.e_commerce.application.command.user.UpdateUserCommand;
import backend.e_commerce.infrastructure.out.persistence.user.entity.AddressEntity;
import backend.e_commerce.infrastructure.out.persistence.user.entity.UserEntity;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService implements UserInfoCommandUserUseCase {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public User registerUser(RegisterUserCommand command) {
        User user = command.toDomain();
        return userRepository.save(user);
    }

    @Override
    public User changePassword(ChangePasswordCommand command) {
        User user = userRepository.findById(command.getUserId());

        if (!user.getPassword().equals(command.getOldPassword())) {
                // TODO -
        }

        User changedUser = user.changePassword(command.getNewPassword());
        return userRepository.save(changedUser);
    }

    @Override
    public User updateProfile(Long userId, UpdateUserCommand command) {
        User user = userRepository.findById(userId);

        User changedUser = user.changeUserInfo(command.newName(), command.newPhone());
        return userRepository.save(changedUser);
    }

    @Override
    @Transactional
    public Address addAddress(RegisterAddressCommand command) {
        Address newAddress = Address.addAddress(
                command.getAddress(),
                command.getAddressDetail(),
                command.getZipCode(),
                command.isDefault()
        );

        userRepository.addAddress(command.getUserId(), newAddress);
        return newAddress;
    }

    @Override
    @Transactional
    public void removeAddress(Long userId, Long addressId) {
        userRepository.removeAddress(userId, addressId);
    }
}