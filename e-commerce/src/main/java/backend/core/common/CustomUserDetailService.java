package backend.core.common;

import backend.e_commerce.application.port.out.UserPersistencePort;
import backend.e_commerce.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final UserPersistencePort userRepository;
//    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            User user = userRepository.findByEmail(email);
            System.out.println("000000000000000000000000000000000000000000000");
            System.out.println("user.getEmail "+user.getEmail());
            System.out.println("user.getEmail "+user.getPassword());
            System.out.println("000000000000000000000000000000000000000000000");
            return createUserDetails(user);
        } catch (IllegalArgumentException e) {
            throw new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다: " + email);
        }
    }

    private UserDetails createUserDetails(User user) {
        return new CustomUserDetails(user);
    }
}
