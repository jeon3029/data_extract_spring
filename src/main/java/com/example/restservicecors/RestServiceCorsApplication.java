package com.example.restservicecors;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;


@SpringBootApplication
public class RestServiceCorsApplication implements CommandLineRunner{
	private static final Logger log = LoggerFactory.getLogger(RestServiceCorsApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(RestServiceCorsApplication.class, args);
	}
	
//	@Autowired
//	JdbcTemplate jdbc;
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/greeting-javaconfig").allowedOrigins("http://localhost:8081");
			}
		};
	}
	
	
	//부트시점 실행되는 코드
	@Override
	public void run(String... args) throws Exception {
		log.info("서버 실행중");
	}
}
