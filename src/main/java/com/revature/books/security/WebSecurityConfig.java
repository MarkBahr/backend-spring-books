package com.revature.books.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.boot.CommandLineRunner;
// import java.time.LocalDate;

import com.revature.books.security.jwt.AuthEntryPointJwt;
import com.revature.books.security.jwt.AuthTokenFilter;
import com.revature.books.repository.RoleRepository;
import com.revature.books.repository.UserRepository;
import com.revature.books.model.ERole;
import com.revature.books.model.Role;
import com.revature.books.model.User;

/**
 * This class enables web security and sets security settings for JWT, sets up a filter chain,
 * provides a password encoder, 
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
(securedEnabled = true,
jsr250Enabled = true,
prePostEnabled = true) // by default
public class WebSecurityConfig {

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    
  /**
   * This method defines security filters applied to requests and responses. Applies security logic before reaching controllers. 
   * We add JWT auth filter, disable csrf, set session policy to stateless, and tell which endpoints are made public
   * @param http
   * @return
   * @throws Exception
   */
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf ->
        csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .ignoringRequestMatchers("/api/auth/public/**")
        );
    http.exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler));
    http.authorizeHttpRequests(auth -> 
          auth.requestMatchers("/api/admin/**").hasRole("ADMIN")
              .requestMatchers("/api/auth/**").permitAll()
              .requestMatchers("/api/test/**").permitAll()
              .requestMatchers("/api/csrf-token").permitAll()
              .anyRequest().authenticated()
        );
    http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    http.formLogin(withDefaults());
    http.httpBasic(withDefaults());
    return http.build();
  }

    // Create Authentication Manager object, which delegates auth logic to auth providers
  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
    return authConfig.getAuthenticationManager();
  }

  // Encode passwords using BCrypt
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }


  @Bean
    public CommandLineRunner initData(RoleRepository roleRepository,
                                      UserRepository userRepository,
                                      PasswordEncoder passwordEncoder) {
        return args -> {
            Role userRole = roleRepository.findByRoleName(ERole.ROLE_USER)
                    .orElseGet(() -> roleRepository.save(new Role(ERole.ROLE_USER)));

            Role adminRole = roleRepository.findByRoleName(ERole.ROLE_ADMIN)
                    .orElseGet(() -> roleRepository.save(new Role(ERole.ROLE_ADMIN)));

            if (!userRepository.existsByUsername("user1")) {
                User user1 = new User("user1", "user1@example.com",
                        passwordEncoder.encode("password1"));
                user1.setRole(userRole);
                userRepository.save(user1);
            }

            if (!userRepository.existsByUsername("admin")) {
                User admin = new User("admin", "admin@example.com",
                        passwordEncoder.encode("adminPass"));
                admin.setRole(adminRole);
                userRepository.save(admin);
            }
        };
    }
}
