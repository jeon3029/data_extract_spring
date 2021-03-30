package com.himart.restservicecors.dao;


import org.apache.ibatis.annotations.Mapper;
import com.himart.restservicecors.dto.LogDto;

@Mapper
public interface LogDao {
	public boolean insertLog(LogDto logDto);
}
