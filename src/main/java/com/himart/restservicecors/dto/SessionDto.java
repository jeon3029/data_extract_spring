package com.himart.restservicecors.dto;

public class SessionDto {
	private int sid;
	private int session;
	public SessionDto(){}
	public SessionDto(int si,int se){
		sid = si; session = se;
	}
	public int getSid() {
		return sid;
	}
	public int getSession() {
		return session;
	}
}
