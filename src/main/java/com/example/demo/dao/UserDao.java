package com.example.demo.dao;


import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Configuration;

import com.example.demo.bean.User;

public interface UserDao {

	List<User> getByMap(Map<String, Object> map);
	User getById(Integer id);
	Integer create(User user);
	int update(User user);
	int delete(Integer id);
	User getByUserName(String userName);
	public User selectUserByLoginName(String userName);
}