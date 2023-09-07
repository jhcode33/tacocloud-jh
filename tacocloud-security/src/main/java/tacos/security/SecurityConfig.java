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
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

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
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .authorizeHttpRequests(authorize -> authorize
                    // 서버가 어떤 method, header, content type을 지원하는지 확인한다
                    .requestMatchers(HttpMethod.OPTIONS).permitAll() // needed for Angular/CORS
                    .requestMatchers(HttpMethod.POST, "/api/ingredients").permitAll()

                    //== restclient api ==
                    .requestMatchers("/tacos/recents").permitAll()
                    .requestMatchers("/tacos/recent").permitAll()

                    //== login ==//
                    .requestMatchers("/register/**").permitAll()
                    .requestMatchers("/customLogin").permitAll()

                    .requestMatchers("/design", "/orders/**")
                    .permitAll()

                        //.hasRole("USER")
                      //.access("hasRole('ROLE_USER')")

                    .requestMatchers(HttpMethod.PATCH, "/ingredients").permitAll()
                    .requestMatchers("/**").permitAll()
                    .anyRequest().authenticated()
            )
//            .formLogin(formLogin -> formLogin
//                    .loginProcessingUrl("/login")
//            )
            .httpBasic(httpBasic -> httpBasic
                    .realmName("Taco Cloud")
            )
            .logout(logout -> logout
                    .logoutSuccessUrl("/")
            )
            .csrf(csrf -> csrf.disable()
//                    .ignoringRequestMatchers("/h2-console/**",
//                                                       "/ingredients/**",
//                                                       "/design",
//                                                       "/orders/**",
//                                                       "/api/**",
//                                                       "/tacos/**",
//                                                       "/register/**",
//                                                       "/customLogin"
//                                             )
            )
            // 동일한 출처일 경우, web hijacking을 방지하기 위해
            .headers(headers -> headers
                    .frameOptions(frameOptions -> frameOptions
                            .sameOrigin()
                    )
            )
           .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED));

    return http.build();
  }

  //== Cors Filter ==//
//  @Bean
//  public CorsFilter corsFilter() {
//    CorsConfiguration config = new CorsConfiguration();
//    config.addAllowedOrigin("http://localhost:4200"); // Angular 클라이언트의 Origin
//    config.addAllowedMethod("*"); // 허용할 HTTP 메서드
//    config.addAllowedHeader("*"); // 허용할 헤더
//    config.setAllowCredentials(true); // 인증 정보 허용
//
//    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//    source.registerCorsConfiguration("/**", config);
//
//    return new CorsFilter(source);
//  }

  //== CORS config ==//
  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
      CorsConfiguration config = new CorsConfiguration();
      config.setAllowedOrigins(Arrays.asList("http://localhost:8080", "http://localhost:4200"));
      config.setAllowedHeaders(Arrays.asList("*"));
      config.setAllowedMethods(Arrays.asList("GET","POST", "PUT", "DELETE"));
      config.setAllowCredentials(true);

      UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
      source.registerCorsConfiguration("/**", config);
      return source;
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
