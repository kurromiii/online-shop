package com.mftplus.onlineshop.configuration;

import com.mftplus.onlineshop.service.Impl.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
//    private final GoogleOath2SuccessHandler googleOath2SuccessHandler;
    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/", "/shop/**", "register").permitAll()
                        .requestMatchers("/admin/**").hasAuthority("ADMIN_ROLE")
                        .requestMatchers("/user/**").hasAuthority("USER_ROLE")
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .permitAll()
                        .failureUrl("/login?error= true")
                        .defaultSuccessUrl("/")
                        .usernameParameter("email")
                        .passwordParameter("password")
                )
//                .oauth2Login((oauth2Login) -> oauth2Login
//                                .loginPage("/login")
//                                .successHandler(googleOath2SuccessHandler)
//                        )
                .logout(
                        (logout) -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .logoutSuccessUrl("/login")
                                .invalidateHttpSession(true)
                                .deleteCookies("JSESSIONID")
                )
                .exceptionHandling(ex -> ex.accessDeniedHandler(accessDeniedHandler()))
                .csrf(csrf -> csrf.disable());

        return http.build();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return null;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        // retrieve builder from httpSecurity
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder
                .userDetailsService(customUserDetailsService);
        return authenticationManagerBuilder.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/resources/**", "/static/**", "/images/**","/productImages/**"
        ,"/css/**", "/js/**");
    }

}
