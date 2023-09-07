package tacos.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

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

                              .pathMatchers("/design", "/orders/**").permitAll()
                              .pathMatchers(HttpMethod.PATCH, "/ingredients").permitAll()
                              .pathMatchers("/**").permitAll()
                              .anyExchange().authenticated()
              )
              .cors(cors -> cors.disable())
              .build();
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
