package com.himart.restservicecors.dao;

import java.util.HashMap;

import org.springframework.stereotype.Component;

import com.himart.restservicecors.dto.SessionDto;

@Component
public class SessionDao {
	private HashMap<Integer,SessionDto> sessionDao = new HashMap<>();
	
	SessionDao(){
		System.out.println("session init");
	}
	
	public boolean checkId(Integer id) {
		return sessionDao.containsKey(id);
	}
	public void setId(int id,int sid,int session) {
		sessionDao.put(id, new SessionDto(sid,session));
	}
	public void removeId(int id) {
		if(sessionDao.containsKey(id))sessionDao.remove(id);
	}
	public SessionDto getSession(int id) {
		if(sessionDao.containsKey(id)) return sessionDao.get(id);
		else return null;
	}
}
