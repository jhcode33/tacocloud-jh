package tacos.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

  @Bean
  @Lazy
  public PasswordEncoder encoder() {
    //  return new StandardPasswordEncoder("53cr3t");
    return NoOpPasswordEncoder.getInstance();
  }

  @Bean
  public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) throws Exception {
      return http
              .authorizeExchange(exchanges ->
                      exchanges
                              .pathMatchers(HttpMethod.OPTIONS).permitAll() // needed for Angular/CORS
                              .pathMatchers(HttpMethod.POST, "/api/ingredients").permitAll()

                              //== restclient api ==
                              .pathMatchers("/tacos/recents").permitAll()
                              .pathMatchers("/tacos/recent").permitAll()

                              //== login ==//
                              .pathMatchers("/register/**").permitAll()
                              .pathMatchers("/customLogin").permitAll()

                              .pathMatchers("/design/**", "/orders/**").permitAll()
                              .pathMatchers(HttpMethod.PATCH, "/ingredients").permitAll()
                              .pathMatchers("/**").permitAll()
                              .anyExchange().permitAll()
              )
              .cors(corsSpec -> corsWebFilter())
              .csrf(csrfSpec -> csrfSpec.disable())
              .httpBasic(Customizer.withDefaults())
              .build();
  }

  //== Cors Filter ==//
  @Bean
  public CorsWebFilter corsWebFilter() {
      CorsConfiguration corsConfig = new CorsConfiguration();
      corsConfig.setAllowCredentials(true);
      corsConfig.addAllowedOrigin("http://localhost:4200"); // 실제 프론트엔드 애플리케이션의 출처로 변경
      corsConfig.addAllowedHeader("*"); // 모든 헤더 허용
      corsConfig.addAllowedMethod("*"); // 모든 HTTP 메서드 허용

      // 경로 매칭 설정
      CorsConfigurationSource source = request -> corsConfig;
      return new CorsWebFilter(source);
  }

//  //== CORS config ==//
//  @Bean
//  public CorsConfigurationSource corsConfigurationSource() {
//      CorsConfiguration config = new CorsConfiguration();
//      config.setAllowedOrigins(Arrays.asList("http://localhost:8080", "http://localhost:4200"));
//      config.setAllowedHeaders(Arrays.asList("*"));
//      config.setAllowedMethods(Arrays.asList("GET","POST", "PUT", "DELETE"));
//      config.setAllowCredentials(true);
//
//      UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//      source.registerCorsConfiguration("/**", config);
//      return source;
//  }


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
