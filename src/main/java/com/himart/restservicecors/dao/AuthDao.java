package com.himart.restservicecors.dao;

import org.apache.ibatis.annotations.Mapper;

import com.himart.restservicecors.dto.AuthDto;

@Mapper
public interface AuthDao {
	public boolean checkAuth(AuthDto authDto);
}
