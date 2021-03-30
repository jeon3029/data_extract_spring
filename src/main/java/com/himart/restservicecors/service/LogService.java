package com.himart.restservicecors.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.himart.restservicecors.dao.LogDao;
import com.himart.restservicecors.dto.LogDto;

@Service
public class LogService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private LogDao logDao;
	
	public void insertLog(LogDto logDto) {
		logDao.insertLog(logDto);
	}
}
