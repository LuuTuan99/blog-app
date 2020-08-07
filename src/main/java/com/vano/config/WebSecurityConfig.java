package com.vano.config;

import com.vano.entity.BlogAuthor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected UserDetailsService userDetailsService() {
        return new MyUserDetailService();
    }

    @Bean
    AuthenticationFailureHandler authenticationFailureHandler() {
        return new MyAuthenticationFailureHandler();
    }
    @Bean
    protected AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new MyAuthenticationSuccessHandle();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                        .antMatchers(
                        "/*",
                        "/blogAuthor/register.html",
                        "/assets/**",
                        "/assets1/**",
                        "/js/**",
                        "/css/**",
                        "/fonts/**",
                        "/img/**",
                        "/plugins/**").permitAll()
                        .antMatchers("/admin/**").hasAnyRole(BlogAuthor.Role.ADMIN.getValue())
                        .anyRequest().authenticated()
                .and()
                .exceptionHandling().and()
                .headers().frameOptions().disable().and()
                .formLogin()
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .loginPage("/blogAuthor/login.html")
                        .successHandler(authenticationSuccessHandler())
                        .permitAll()

                .and()
                .logout()
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/blogAuthor/login.html")
                        .permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedPage("/403");
    }
}
