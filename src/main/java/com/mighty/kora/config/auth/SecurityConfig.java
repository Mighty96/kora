package com.mighty.kora.config.auth;

import com.mighty.kora.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable() // h2-console 사용
                .and()
                    .formLogin()
                        .loginPage("/signin")
                .and()
                    .authorizeRequests() // antMatchers를 사용하기 위해 선언
                        .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/signin", "/signup", "/api/signup/**", "/api/login", "/authConfirm").permitAll() // 지정 URL들은 전체 열람 권한
                        .antMatchers("/signup_auth").hasRole(Role.GUEST.name())
                        .antMatchers("/api/**").hasRole(Role.USER.name()) // USER권한을 가진 사람만 열람
                        .anyRequest().hasAnyAuthority() // 나머지는 인증된 사용자(로그인)
                .and()
                    .logout()
                        .logoutSuccessUrl("/")
                .and()
                    .oauth2Login()
                        .userInfoEndpoint()
                            .userService(customOAuth2UserService)
                .and()
                    .successHandler(new UserLoginSuccessHandler())
                .and()
                    .exceptionHandling()
                        .accessDeniedHandler(new CustomAccessDeniedHandler())
                        .authenticationEntryPoint(new CustomAuthenticationEntryPoint());

    }

    // 비밀번호 암호화
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
