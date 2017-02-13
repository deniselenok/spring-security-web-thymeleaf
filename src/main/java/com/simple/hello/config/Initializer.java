package com.simple.hello.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("com.simple.hello")
@EnableJpaRepositories("com.simple.hello.repository")
@EntityScan("com.simple.hello.domain")
@EnableConfigurationProperties
@EnableAutoConfiguration
public class Initializer extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(Initializer.class, args);
	}
}
