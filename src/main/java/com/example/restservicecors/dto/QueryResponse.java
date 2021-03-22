package com.example.restservicecors.dto;

import java.util.ArrayList;
import java.util.List;

public class QueryResponse {
	private String status;
	private Integer colNum;
//	private String[] header;
//	private ArrayList[] data;
	
	public QueryResponse(){
		status = "success";
		colNum = 0;
//		header = null;
//		data = null;
	}
	public void setStatus(String s) {
		this.status = s;
	}
//	public void setHeader(String[] h) {
//		this.header = h;
//	}
//	public void setData(ArrayList[] d) {
////		this.data = d;
//	}
	public void setColNum(Integer i) {
		this.colNum = i;
	}
}
