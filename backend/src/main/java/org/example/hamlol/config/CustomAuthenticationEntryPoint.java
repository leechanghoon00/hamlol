package org.example.hamlol.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        // 🔒 이미 응답이 커밋된 경우는 무시
        if (response.isCommitted()) {
            return;
        }

        // 🔧 응답 상태 및 헤더 설정
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        // 🧾 응답 바디 구성
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "접근이 거부되었습니다. 로그인이 필요합니다.");

        try {
            response.getWriter().write(mapper.writeValueAsString(errorResponse));
        } catch (IOException ex) {
            // 로그로 남기거나 무시
            ex.printStackTrace(); // 선택
        }
    }
}
