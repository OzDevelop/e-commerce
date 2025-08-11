package backend.core.common.Response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ApiResponse<T> {
    private LocalDateTime timestamp = LocalDateTime.now();
    private String path;
    private String status;
    private T body;

    @Builder
    public ApiResponse(String path, String status, T body) {
        this.path = path;
        this.status = status;
        this.body = body;
    }
}
