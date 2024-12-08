package vuquochuy.week05_vuquochuy.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    private static final String CANDIDATE_ROLE = "CANDIDATE";
    private static final String COMPANY_ROLE = "COMPANY";

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userDetailsService);
        auth.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        return auth;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(registry -> registry
                        // Quyền truy cập công khai
                        .requestMatchers("/register", "/login", "/").permitAll()
                        // Quyền truy cập cho vai trò "user"
                        .requestMatchers("/candidate/**").hasRole(CANDIDATE_ROLE)
                        // Quyền truy cập cho vai trò "company"
                        .requestMatchers("/company/**").hasRole(COMPANY_ROLE)
                        // Mọi yêu cầu khác phải được xác thực
                        .anyRequest().authenticated()
                )
                .formLogin(formLoginConfigurer -> formLoginConfigurer
                        .loginPage("/login") // Trang login
                        .loginProcessingUrl("/do-login") // URL xử lý form login
                        .defaultSuccessUrl("/default", true) // Chuyển hướng dựa trên role
                        .permitAll()
                )
                .logout(logout -> logout
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/login?logout").permitAll()
                )
                .csrf(csrf -> csrf.disable()) // Tat CSRF
                .sessionManagement(session -> session
                        .maximumSessions(1) // Hạn chế mỗi tài khoản chỉ đăng nhập được một nơi
                );

        return http.build();
    }

}
