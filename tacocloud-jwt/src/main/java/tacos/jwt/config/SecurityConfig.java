package tacos.jwt.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    // PasswordEncoder는 BCryptPasswordEncoder를 사용
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .exceptionHandling(except -> except.authenticationEntryPoint(jwtAuthenticationEntryPoint)
                                               .accessDeniedHandler(jwtAccessDeniedHandler))
            .headers(headers -> headers
                    .frameOptions(frameOptions -> frameOptions
                            .sameOrigin()
            ))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(authorize -> authorize
                // 서버가 어떤 method, header, content type을 지원하는지 확인한다
                .requestMatchers(HttpMethod.OPTIONS).permitAll() // needed for Angular/CORS

                .requestMatchers(HttpMethod.POST, "/api/ingredients").permitAll()
                .requestMatchers("/register/**").permitAll()
                .requestMatchers("/customLogin").permitAll()

                .requestMatchers("/tacos/recents").permitAll()
                .requestMatchers("/tacos/recent").permitAll()
                .requestMatchers("/design", "/orders/**")/*.permitAll()*/
                .hasAnyRole("hasRole('ROLE_USER')")
                //.access("hasRole('ROLE_USER')")

                .requestMatchers(HttpMethod.PATCH, "/ingredients").permitAll()
                .requestMatchers("/**").permitAll()
            )
            .httpBasic(httpBasic -> httpBasic
                    .realmName("Taco Cloud")
            )
            .logout(logout -> logout
                    .logoutSuccessUrl("/")
            )
            .apply(new JwtSecurityConfig(tokenProvider));

        return http.build();
    }

}