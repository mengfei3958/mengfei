package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.StudentMapper;
import com.example.demo.entity.Student1;

@Service
public class StuServiceImp {

	@Autowired
	private StudentMapper studentMapper;
	
	public List<Student1> findAll(){
		return studentMapper.findAll();
	}
}
