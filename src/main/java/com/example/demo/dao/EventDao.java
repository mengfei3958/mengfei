package com.example.demo.dao;


import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Configuration;

import com.example.demo.bean.Event;

public interface EventDao {

	List<Event> getByMap(Map<String, Object> map);
	Event getById(Integer id);
	Integer create(Event event);
	int update(Event event);
	int delete(Integer id);
}