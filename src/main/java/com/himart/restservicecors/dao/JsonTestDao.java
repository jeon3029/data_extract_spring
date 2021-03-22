package com.himart.restservicecors.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.himart.restservicecors.dto.SampleDto;
import com.himart.restservicecors.dto.TestDto;


@Mapper
public interface JsonTestDao {
	public List<SampleDto> getJsonTest();
}
