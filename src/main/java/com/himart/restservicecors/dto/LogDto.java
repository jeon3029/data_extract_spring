package com.himart.restservicecors.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class LogDto {
	private int qCode;
	private String hrusrId;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private Date reqDt;
	private String reqPur;
}

