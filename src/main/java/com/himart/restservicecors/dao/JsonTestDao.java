package com.himart.restservicecors.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.himart.restservicecors.dto.MapperTestDto;

@Mapper
public interface JsonTestDao {
	public List<MapperTestDto> getJsonTest();
}
