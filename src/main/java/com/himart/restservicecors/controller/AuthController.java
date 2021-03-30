package com.himart.restservicecors.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public boolean checkAuth(HttpSession httpSession,
    		@RequestBody AuthDto authDto
    		) throws Exception{
		boolean b = authService.checkAuth(authDto);
		logger.info("CHECK AUTH" + authDto + " : " + b);
    	return b;
    }
	
	@PostMapping("/auth")
    public Object insertAuth(HttpSession httpSession,
    		@RequestBody AuthDto authDto
    		) throws Exception{
		logger.info("INSERT AUTH" + authDto);
    	if(authService.insertAuth(authDto)==false) {
    		logger.info("INSERT AUTH" + authDto + "fail - Already Exists");
    		return new ResponseEntity<>("already exists", HttpStatus.OK);
    	}
    	else {
    		logger.info("INSERT AUTH" + authDto + "success");
    		return new ResponseEntity<>("success", HttpStatus.OK);
    	}
    }
	
	@DeleteMapping("/auth")
    public Object deleteAuth(HttpSession httpSession,
    		@RequestBody AuthDto authDto
    		) throws Exception{
		logger.info("DELETE AUTH" + authDto);
		if(authService.deleteAuth(authDto)==false) {
			logger.info("DELETE AUTH" + authDto + "fail - Not Exists");
    		return new ResponseEntity<>("not exists", HttpStatus.OK);
    	}
    	else {
    		logger.info("DELETE AUTH" + authDto + "success");
    		return new ResponseEntity<>("success", HttpStatus.OK);
    	}
    }
	@GetMapping("/auth/all/{qcode}")
    public List<String> getAuthList(HttpSession httpSession,
    		@PathVariable int qcode
    		) throws Exception{
		logger.info("SELECT ALL auth from Qcode : " + qcode);
		return authService.selectAllAuth(qcode);
    }
	
}
