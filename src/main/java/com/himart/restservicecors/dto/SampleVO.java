package com.himart.restservicecors.dto;


public class SampleVO{	
	private Integer no;
	private String name;
	public SampleVO(){
		no=1;
		name="test";
	}
	public Integer getNo() {
		return no;
	}
	public void setNo(Integer no) {
		this.no = no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}