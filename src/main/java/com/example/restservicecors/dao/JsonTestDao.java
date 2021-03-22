package com.example.restservicecors.dao;

import java.util.List;
import com.example.restservicecors.dto.SampleDto;

import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface JsonTestDao {
	public List<SampleDto> getJsonTest();
}
