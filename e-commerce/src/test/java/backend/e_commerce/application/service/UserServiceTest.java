package backend.e_commerce.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import backend.e_commerce.application.command.user.ChangePasswordCommand;
import backend.e_commerce.application.command.user.RegisterUserCommand;
import backend.e_commerce.application.command.user.RegisterUserCommand.AddressCommand;
import backend.e_commerce.application.command.user.UpdateUserCommand;
import backend.e_commerce.application.port.out.UserRepository;
import backend.e_commerce.domain.user.User;
import backend.e_commerce.domain.user.UserRole;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private RegisterUserCommand registerUserCommand;
    private ChangePasswordCommand changePasswordCommand;
    private Long userId;
    private User expectedUser;

    @BeforeEach
    void setUp() {
        // given
        userId = 1L;

        changePasswordCommand = new ChangePasswordCommand(
                userId,
                "password",
                "password123"
        );

        registerUserCommand = new RegisterUserCommand(
                "test@example.com",
                "password",
                "홍길동",
                "010-1234-5678",
                UserRole.USER,
                List.of(new AddressCommand(
                        "서울특별시 강남구 테헤란로 123",
                "101동 202호",
                "06134",
                true
                ))
        );

        expectedUser = User.createUser(
                registerUserCommand.email(),
                registerUserCommand.password(),
                registerUserCommand.name(),
                registerUserCommand.phone(),
                registerUserCommand.role(),
                registerUserCommand.toAddressDomainList()
        );
    }

    @Test
    void 사용자_회원가입_테스트() {
        given(userRepository.save(any(User.class))).willReturn(expectedUser);

        // when
        User result = userService.registerUser(registerUserCommand);

        // then
        assertEquals(expectedUser.getEmail(), result.getEmail());
        assertEquals(expectedUser.getName(), result.getName());

        verify(passwordEncoder).encode(registerUserCommand.password());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void 사용자_비밀번호변경_테스트() {
        // given
        String oldPassword = "password";
        String newPassword = "password123";

        User existedUser = mock(User.class);
        User changeUser = mock(User.class);

        given(existedUser.getPassword()).willReturn(oldPassword);
        given(userRepository.findById(userId)).willReturn(existedUser);

        given(existedUser.changePassword(newPassword)).willReturn(changeUser);
        given(userRepository.save(changeUser)).willReturn(changeUser);

        // when
        User result = userService.changePassword(changePasswordCommand);

        // then
        assertEquals(changeUser, result);

        verify(existedUser).changePassword(newPassword);
        verify(userRepository).save(changeUser);
    }

    @Test
    void 사용자_정보수정_테스트() {
        // given
        UpdateUserCommand updateUserCommand = new UpdateUserCommand("김길동", "010-9999-9999");

        User existingUser = mock(User.class);
        User changedUser = mock(User.class);

        given(userRepository.findById(userId)).willReturn(existingUser);
        given(existingUser.changeUserInfo(updateUserCommand.newName(), updateUserCommand.newPhone())).willReturn(changedUser);
        given(userRepository.save(changedUser)).willReturn(changedUser);

        // when
        User result = userService.updateProfile(userId, updateUserCommand);

        // then
        assertEquals(changedUser, result);

        verify(existingUser).changeUserInfo(updateUserCommand.newName(), updateUserCommand.newPhone());
        verify(userRepository).save(changedUser);
    }
}