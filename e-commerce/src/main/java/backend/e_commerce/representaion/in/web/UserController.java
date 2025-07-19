package backend.e_commerce.representaion.in.web;

import backend.e_commerce.application.command.user.RegisterUserCommand;
import backend.e_commerce.application.port.in.user.UserInfoCommandUserUseCase;
import backend.e_commerce.domain.user.User;
import backend.e_commerce.representaion.request.RegisterUserRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserInfoCommandUserUseCase userInfoCommandUserUseCase;

    @PostMapping("/register")
    //TODO - 공통 Response 객체 구현 시 변경 예정
    //TODO - ResponseDTO 구현 필요.
    public User registerUser(@RequestBody RegisterUserRequestDto request) {
        RegisterUserCommand command = request.toRegisterUserCommand();

        return userInfoCommandUserUseCase.registerUser(command);
    }

    //TODO - User 정보 수정


}
