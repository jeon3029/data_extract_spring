package com.himart.restservicecors.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.himart.restservicecors.dao.AuthDao;
import com.himart.restservicecors.dto.AuthDto;

@Service
public class AuthService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private AuthDao authDao;
	
	public boolean checkAuth(AuthDto authDto) {
		return authDao.checkAuth(authDto);
	}
	public boolean insertAuth(AuthDto authDto) {
		//이미 있는 경우
		if(checkAuth(authDto)) {
			return false;
		}
		authDao.insertAuth(authDto);
		return true;
	}
	public boolean deleteAuth(AuthDto authDto) {
		//없는경우
		if(!checkAuth(authDto)) {
			return false;
		}
		authDao.deleteAuth(authDto);
		return true;
	}
	public List<String> selectAllAuth(int qcode){
		return authDao.selectAllAuth(qcode);
	}
}
