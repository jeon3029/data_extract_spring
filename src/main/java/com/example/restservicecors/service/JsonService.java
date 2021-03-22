package com.example.restservicecors.service;

import java.util.HashMap;
import java.util.List;

public class JsonService {
	public  List<HashMap<Object, Object>> getLike(int uid, int pid) {
        return likeDao.getLike(uid, pid);
    }
}
