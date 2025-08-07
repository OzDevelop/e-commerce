package backend.core.common.Response;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class ApiResponse<T> {
    private String status;
    private T body;
    private LocalDateTime timestamp = LocalDateTime.now();

    public ApiResponse(String status, T body) {
        this.status = status;
        this.body = body;
    }
}
