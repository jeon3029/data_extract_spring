package com.himart.restservicecors.dto;
//추후 삭제(테스트용)
public class GreetingDto {

	private final long id;
	private final String content;

	public GreetingDto() {
		this.id = -1;
		this.content = "";
	}

	public GreetingDto(long id, String content) {
		this.id = id;
		this.content = content;
	}

	public long getId() {
		return id;
	}

	public String getContent() {
		return content;
	}
}
