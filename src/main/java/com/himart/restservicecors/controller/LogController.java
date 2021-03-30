package com.himart.restservicecors.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.himart.restservicecors.dto.LogDto;
import com.himart.restservicecors.service.LogService;

@RestController
public class LogController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	LogService logService;
	
	@PostMapping("/log")
    public void insertAuth(HttpSession httpSession,
    		@RequestBody LogDto logDto
    		) throws Exception{
		logger.info(""+logDto);
		logService.insertLog(logDto);
		logger.info("INSERT LOG : " + logDto + "success");
    }
}
