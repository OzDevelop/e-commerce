package backend.e_commerce.application.service;

import backend.e_commerce.application.port.in.user.UserInfoCommandUserUseCase;
import backend.e_commerce.application.port.out.UserRepository;
import backend.e_commerce.domain.user.Address;
import backend.e_commerce.domain.user.User;
import backend.e_commerce.application.command.user.RegisterUserCommand;
import backend.e_commerce.application.command.user.UpdateUserCommand;
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
    public User changePassword(Long userId, String newPassword) {
        User user = userRepository.findById(userId);

        User changedUser = user.changePassword(newPassword);
        return userRepository.save(changedUser);
    }

    @Override
    public User updateProfile(Long userId, UpdateUserCommand command) {
        User user = userRepository.findById(userId);

        User changedUser = user.changeUserInfo(command.newName(), command.newPhone());
        return userRepository.save(changedUser);
    }
}