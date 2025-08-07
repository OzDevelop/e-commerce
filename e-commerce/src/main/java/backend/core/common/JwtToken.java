package backend.core.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

/**
 * JwtToken DTO
 */

@Builder
@Data
@AllArgsConstructor
public class JwtToken {
    private String grantType;   // Jwt 인증 타입(Bearer 사용 예정)
    private String accessToken;
    private String refreshToken;
}
