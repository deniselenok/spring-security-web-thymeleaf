package com.simple.hello.config.security;

import com.simple.hello.config.security.extra.CryptTool;
import com.simple.hello.config.security.extra.CustomAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    public static final String FORM_BASED_LOGIN_ENTRY_POINT = "/login";
    public static final String FORM_BASED_SIGN_UP_ENTRY_POINT = "/register";

    @Autowired
    private SecuritySettings securitySettings;

    @Autowired
    private CustomAuthenticationProvider authenticationProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .exceptionHandling()
                .and()
                .headers().frameOptions().disable() //remove, this only for H2 Console Dash-board [ only for testing]
                .and()
                .authorizeRequests()
                .antMatchers(FORM_BASED_SIGN_UP_ENTRY_POINT).permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage(FORM_BASED_LOGIN_ENTRY_POINT)
                .permitAll();
    }

    @Bean
    public CryptTool getCryptTool() {
        return CryptTool.createCryptTool(securitySettings.getPasswordSecretKey());
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(this.authenticationProvider);
    }

}
