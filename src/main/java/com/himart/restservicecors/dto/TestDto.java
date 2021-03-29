package com.himart.restservicecors.dto;

import lombok.Data;

//추후 삭제(테스트용)
@Data
public class TestDto{	
	private Integer no;
	private String name;
	public TestDto(){
		no=1;
		name="test";
	}
}