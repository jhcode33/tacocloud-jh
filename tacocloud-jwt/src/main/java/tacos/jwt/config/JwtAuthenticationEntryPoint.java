package tacos.jwt.config;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
	// EntiyPoint : 어떤 동작이 시작되는지, 어떻게 응답할지
	// AuthenticationEntryPoint : 인증 예외가 발생했을 때, 어떤 동작을 수행할지 정의하는 클래스
	// HttpBasicAuthenticationEntryPoint, LoginUrlAuthenticationEntryPoint, HttpStatusEntryPoint 등 기본적으로 제공해주는게 있음
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        // 유효한 자격증명을 제공하지 않고 접근하려 할때 401
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }
}