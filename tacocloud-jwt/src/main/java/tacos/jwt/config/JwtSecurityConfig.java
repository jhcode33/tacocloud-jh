package tacos.jwt.config;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtSecurityConfig extends 
	SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
	
    private final TokenProvider tokenProvider;

    @Override
    public void configure(HttpSecurity http) {
    	//http.addFilterBefore(null, null)

        // securityFilterChain에 UsernamePasswordAuthenticationFilter 필터 앞에 JwtFilter 등록
        http.addFilterBefore(
                new JwtFilter(tokenProvider),
                UsernamePasswordAuthenticationFilter.class
        );
    }
}
