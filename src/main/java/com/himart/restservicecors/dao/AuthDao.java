package com.himart.restservicecors.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.himart.restservicecors.dto.AuthDto;

@Mapper
public interface AuthDao {
	public boolean checkAuth(AuthDto authDto);
	public void insertAuth(AuthDto authDto);
	public void deleteAuth(AuthDto authDto);
	public List<String> selectAllAuth(int qCode);
}
