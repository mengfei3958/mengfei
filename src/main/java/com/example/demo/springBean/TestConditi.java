package com.example.demo.springBean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.entity.Student;

@Configuration
public class TestConditi {

	@Bean
	public Student student2(){
		System.out.println("实例化student2------------------------------");
		return new Student();
	}
	
	@Bean
	public Student student3(){
		System.out.println("实例化student3------------------------------");
		return new Student();
	}
}
