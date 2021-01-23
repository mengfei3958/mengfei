package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Student1;

public interface StudentService {

	List<Student1> getAll();
	List<Student1> findAllByName(String name);
	List<Student1> findAllJoin();
}
