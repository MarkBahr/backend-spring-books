package com.secure.books.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
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

import com.secure.books.models.AppRole;
import com.secure.books.models.Role;
import com.secure.books.models.User;
import com.secure.books.repositories.RoleRepository;
import com.secure.books.repositories.UserRepository;
import com.secure.books.security.jwt.AuthEntryPointJwt;
import com.secure.books.security.jwt.AuthTokenFilter;

import java.time.LocalDate;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * This class enables web security and sets security settings for JWT, sets up a filter chain,
 * provides a password encoder, 
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)
public class SecurityConfig {
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
        SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
                http.csrf(csrf ->
                        csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                                .ignoringRequestMatchers("/api/auth/public/**")
                );
                //http.csrf(AbstractHttpConfigurer::disable);
                http.authorizeHttpRequests((requests)
                        -> requests
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/csrf-token").permitAll()
                        .requestMatchers("/api/auth/public/**").permitAll()
                        .anyRequest().authenticated());
                http.exceptionHandling(exception
                        -> exception.authenticationEntryPoint(unauthorizedHandler));
                http.addFilterBefore(authenticationJwtTokenFilter(),
                        UsernamePasswordAuthenticationFilter.class);
                http.formLogin(withDefaults());
                http.httpBasic(withDefaults());
                return http.build();
        }

        // Create Authentication Manager object, which delegates auth logic to auth providers
        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
                return authenticationConfiguration.getAuthenticationManager();
        }

        // Encode passwords using BCrypt
        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        // create default users with roles if they don't exist
        @Bean
        public CommandLineRunner initData(RoleRepository roleRepository,
                                        UserRepository userRepository,
                                        PasswordEncoder passwordEncoder) {
                return args -> {
                Role userRole = roleRepository.findByRoleName(AppRole.ROLE_USER)
                        .orElseGet(() -> roleRepository.save(new Role(AppRole.ROLE_USER)));

                Role adminRole = roleRepository.findByRoleName(AppRole.ROLE_ADMIN)
                        .orElseGet(() -> roleRepository.save(new Role(AppRole.ROLE_ADMIN)));

                if (!userRepository.existsByUserName("user1")) {
                        User user1 = new User("user1", "user1@example.com",
                                passwordEncoder.encode("password1"));
                        user1.setAccountNonLocked(false);
                        user1.setAccountNonExpired(true);
                        user1.setCredentialsNonExpired(true);
                        user1.setEnabled(true);
                        user1.setCredentialsExpiryDate(LocalDate.now().plusYears(1));
                        user1.setAccountExpiryDate(LocalDate.now().plusYears(1));
                        user1.setTwoFactorEnabled(false);
                        user1.setSignUpMethod("email");
                        user1.setRole(userRole);
                        userRepository.save(user1);
                }

                if (!userRepository.existsByUserName("admin")) {
                        User admin = new User("admin", "admin@example.com",
                                passwordEncoder.encode("adminPass"));
                        admin.setAccountNonLocked(true);
                        admin.setAccountNonExpired(true);
                        admin.setCredentialsNonExpired(true);
                        admin.setEnabled(true);
                        admin.setCredentialsExpiryDate(LocalDate.now().plusYears(1));
                        admin.setAccountExpiryDate(LocalDate.now().plusYears(1));
                        admin.setTwoFactorEnabled(false);
                        admin.setSignUpMethod("email");
                        admin.setRole(adminRole);
                        userRepository.save(admin);
                }
                };
        }
}

