package backend.core.common.Response;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SuccessResponse<T> {
        private final String status;
        private final T body;
}
