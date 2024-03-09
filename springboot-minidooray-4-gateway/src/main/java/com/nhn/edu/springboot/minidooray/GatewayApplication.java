package com.nhn.edu.springboot.minidooray;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableConfigurationProperties(ServerProperties.class)
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(GatewayApplication.class);
		application.run(args);
	}
}
