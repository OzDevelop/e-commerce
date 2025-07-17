package backend.e_commerce.domain.user;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserTest {
    private User user;

    @BeforeEach
    void setUp() {
        String email = "test@example.com";
        String password = "password";
        String name = "홍길동";
        String phone = "010-1234-5678";
        UserRole role = UserRole.USER;

        user = User.createUser(email, password, name, phone, role);
    }

    @Test
    void 사용자_생성_테스트() {
        // then
        assertEquals("test@example.com", user.getEmail());
        assertEquals("password", user.getPassword());
        assertEquals("홍길동", user.getName());
        assertEquals("010-1234-5678", user.getPhone());
        assertEquals(UserRole.USER, user.getRole());

    }

    @Test
    void 사용자프로필_수정_테스트() {
        // when
        User updatedUser = user.changeUserInfo("영희", "010-2222-1111");

        // then
        assertEquals("영희", updatedUser.getName());
        assertEquals("010-2222-1111", updatedUser.getPhone());
    }


    @Test
    void 비밀번호_변경_테스트() {
        // when
        User updatedUser = user.changePassword("newPassword");

        // then
        assertEquals("newPassword", updatedUser.getPassword());
    }

}