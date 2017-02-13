package com.simple.hello.config.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt.security")
@Getter
@Setter
public class SecuritySettings {

    private String passwordSecretKey;

}
