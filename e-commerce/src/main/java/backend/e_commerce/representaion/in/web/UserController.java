package backend.e_commerce.representaion.in.web;

import backend.core.common.JwtToken;
import backend.e_commerce.application.command.user.ChangePasswordCommand;
import backend.e_commerce.application.command.user.RegisterAddressCommand;
import backend.e_commerce.application.command.user.RegisterUserCommand;
import backend.e_commerce.application.port.in.user.UserInfoCommandUserUseCase;
import backend.e_commerce.domain.user.Address;
import backend.e_commerce.domain.user.User;
import backend.e_commerce.representaion.request.user.ChangePasswordRequest;
import backend.e_commerce.representaion.request.user.LoginRequest;
import backend.e_commerce.representaion.request.user.RegisterAddressRequest;
import backend.e_commerce.representaion.request.user.RegisterUserRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserInfoCommandUserUseCase userInfoCommandUserUseCase;

    private final PasswordEncoder passwordEncoder;


    @PostMapping("/register")
    //TODO - 공통 Response 객체 구현 시 변경 예정
    //TODO - ResponseDTO 구현 필요.
    public User registerUser(@RequestBody RegisterUserRequest request) {
        RegisterUserCommand command = request.toRegisterUserCommand();



        return userInfoCommandUserUseCase.registerUser(command);
    }

    @PostMapping("/login")
    public JwtToken loginUser(@RequestBody LoginRequest request) {
        String email = request.getEmail();
        String password = request.getPassword();


        // 비밀번호 매칭 여부 테스트
        boolean matched = passwordEncoder.matches(request.getPassword() , "$2a$10$HAiorqaulkDTykEjsGNRqesQmseTieWF/98ShnegOqVOM/bB4.vvO");
        System.out.println(">>> password matched? " + matched);



        System.out.println(">>> loginUser email: " + request.getEmail());
        System.out.println(">>> loginUser password(raw): " + request.getPassword());
        System.out.println(">>> loginUser password(encoded): " + passwordEncoder.encode(request.getPassword()) );

        System.out.println(">>> password matched? " + matched);

        JwtToken jwtToken = null;

        try {
            jwtToken = userInfoCommandUserUseCase.loginUser(request.getEmail(), request.getPassword());
            System.out.println("Token generated: accessToken=" + jwtToken.getAccessToken() + ", refreshToken=" + jwtToken.getRefreshToken());
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

//        JwtToken jwtToken = userInfoCommandUserUseCase.loginUser(email, password);
//
//        log.info("jwtToken accessToken = {}, refreshToken = {}", jwtToken.getAccessToken(), jwtToken.getRefreshToken());

        return jwtToken;
    }

    //TODO - User 정보 수정
    @PatchMapping("/newPassword")
    public User changePassword(@RequestBody ChangePasswordRequest request) {
        ChangePasswordCommand command = request.toChangePasswordCommand();

        return userInfoCommandUserUseCase.changePassword(command);
    }

    @PostMapping("/address")
    public Address registerAddress(@RequestBody RegisterAddressRequest request) {
        RegisterAddressCommand command = request.toRegisterAddressCommand();
        return userInfoCommandUserUseCase.addAddress(command);
    }

    @DeleteMapping("/{userId}/address/{addressId}")
    public void deleteAddress(@PathVariable Long userId, @PathVariable Long addressId) {
        userInfoCommandUserUseCase.removeAddress(userId, addressId);
    }


}
