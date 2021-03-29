package com.himart.restservicecors.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.himart.restservicecors.dao.AuthDao;
import com.himart.restservicecors.dto.AuthDto;

@Service
public class AuthService {
	@Autowired
	private AuthDao authDao;
	public boolean checkAuth(AuthDto authDto) {
		return authDao.checkAuth(authDto);
	}
}
