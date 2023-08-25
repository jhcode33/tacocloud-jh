package tacos.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation
        .authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web
        .builders.HttpSecurity;
import org.springframework.security.config.annotation.web
        .configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  @Lazy
  public PasswordEncoder encoder() {
    //  return new StandardPasswordEncoder("53cr3t");
    return NoOpPasswordEncoder.getInstance();
  }

  @Bean
  AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
          http
            .cors(cors -> cors.disable())
            .authorizeHttpRequests(authorize -> authorize
                    // 서버가 어떤 method, header, content type을 지원하는지 확인한다
                    .requestMatchers(HttpMethod.OPTIONS).permitAll() // needed for Angular/CORS

                    .requestMatchers(HttpMethod.POST, "/api/ingredients").permitAll()
                    .requestMatchers("/register/**").permitAll()
                    .requestMatchers("/login").permitAll()

                    .requestMatchers("/tacos/recents").permitAll()
                    .requestMatchers("/tacos/recent").permitAll()
                    .requestMatchers("/design", "/orders/**").permitAll()
                    // .access("hasRole('ROLE_USER')

                    .requestMatchers(HttpMethod.PATCH, "/ingredients").permitAll()
                    .requestMatchers("/**").permitAll()
            )
            .formLogin(formLogin -> formLogin
                    .loginPage("/login").permitAll()
            )
            .httpBasic(httpBasic -> httpBasic
                    .realmName("Taco Cloud")
            )
            .logout(logout -> logout
                    .logoutSuccessUrl("/")
            )
            .csrf(csrf -> csrf
                    .ignoringRequestMatchers("/h2-console/**",
                                                       "/ingredients/**",
                                                       "/design",
                                                       "/orders/**",
                                                       "/api/**",
                                                       "/tacos/**",
                                                       "/register/**",
                                                       "/login"
                                             )
            )
            // 동일한 출처일 경우, web hijacking을 방지하기 위해
            .headers(headers -> headers
                    .frameOptions(frameOptions -> frameOptions
                            .sameOrigin()
                    )
            );

    return http.build();
  }


//  @Override
//  protected void configure(HttpSecurity http) throws Exception {
//
//    http
//      .authorizeRequests()
//        .requestMatchers(HttpMethod.OPTIONS).permitAll() // needed for Angular/CORS
//        .requestMatchers("/design", "/orders/**")
//            .permitAll()
//            //.access("hasRole('ROLE_USER')")
//        .requestMatchers(HttpMethod.PATCH, "/ingredients").permitAll()
//        .requestMatchers("/**").access("permitAll")
//
//      .and()
//        .formLogin()
//          .loginPage("/login")
//
//      .and()
//        .httpBasic()
//          .realmName("Taco Cloud")
//
//      .and()
//        .logout()
//          .logoutSuccessUrl("/")
//
//      .and()
//        .csrf()
//          .ignoringRequestMatchers("/h2-console/**", "/ingredients/**", "/design", "/orders/**")
//
//      // Allow pages to be loaded in frames from the same origin; needed for H2-Console
//      .and()
//        .headers()
//          .frameOptions()
//            .sameOrigin()
//      ;
//  }

//  @Bean
//  public PasswordEncoder encoder() {
////    return new StandardPasswordEncoder("53cr3t");
//    return NoOpPasswordEncoder.getInstance();
//  }



//  @Override
//  protected void configure(AuthenticationManagerBuilder auth)
//      throws Exception {
//
//    auth
//      .userDetailsService(userDetailsService)
//      .passwordEncoder(encoder());
//
//  }

}