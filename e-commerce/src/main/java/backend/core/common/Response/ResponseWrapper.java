package backend.core.common.Response;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
public class ResponseWrapper implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // 모든 응답에 감싸기를 적용하되, 다음 예외는 제외
        Class<?> clazz = returnType.getParameterType();

        // ApiResponse로 이미 감싸진 경우 다시 감싸지 않도록 예외 처리
        if (ApiResponse.class.isAssignableFrom(clazz)) {
            return false;
        }

        // byte[] 같은 바이너리 데이터는 감싸지 말기
        if (byte[].class.isAssignableFrom(clazz)) {
            return false;
        }

        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response
    ) {
        String path = request.getURI().getPath();

        if (path.startsWith("/v3/api-docs") || path.startsWith("/swagger-ui") || path.startsWith("/swagger-resources")) {
            return body;
        }

        if (body instanceof ErrorResponse) {
            return ApiResponse.builder()
                    .path(path)
                    .status("ERROR")
                    .body(body)
                    .build();
        }

        return ApiResponse.builder()
                .path(path)
                .status("SUCCESS")
                .body(body)
                .build();
    }
}
