package com.himart.restservicecors.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.himart.restservicecors.dto.AuthDto;
import com.himart.restservicecors.service.AuthService;

@RestController
public class AuthController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	AuthService authService;
	
	@PostMapping("/auth/check")
    public boolean insertQuery(HttpSession httpSession,
    		@RequestBody AuthDto authDto
    		) throws Exception{
    	return authService.checkAuth(authDto);
    }
}
