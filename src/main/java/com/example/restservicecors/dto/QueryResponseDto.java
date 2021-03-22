package com.example.restservicecors.dto;

import java.util.ArrayList;
import java.util.List;

import com.example.restservicecors.dao.QueryDao;

//TODO : 시간 남으면 LOMBOK 사용

public class QueryResponseDto {
	private String status;
	private Integer colNum;
	private Integer rowNum;
	private String[] header;
	private ArrayList[] data;
	private long milTime;
	
	public QueryResponseDto() {
		this.status = "success";
	}
	public void setStaus(String s) {
		status = s;
	}
	public void setColnum(Integer i) {
		colNum = i;
	}
	public void setRownum(Integer i) {
		rowNum = i;
	}
	public void setHeader(String[] h) {
		header = h;
	}
	public void setData(ArrayList[] d) {
		data = d;
	}
	public void setMiltime(long t) {
		milTime = t;
	}
	public String getStatus() {
		return status;
	}
	public Integer getColnum() {
		return colNum;
	}
	public Integer getRownum() {
		return rowNum;
	}
	public String[] getHeader() {
		return header;
	}
	public ArrayList[] getData() {
		return data;
	}
	public long getMiltime() {
		return milTime;
	}
}
