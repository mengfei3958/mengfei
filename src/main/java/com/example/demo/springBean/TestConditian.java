package com.example.demo.springBean;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.entity.Student;
import com.example.demo.shiro.ShiroConfiguration;

@Configuration
//@Configuration用于定义配置类，可替换xml配置文件，被注解的类内部包含有一个或多个被@Bean注解的方法，
//这些方法将会被AnnotationConfigApplicationContext或AnnotationConfigWebApplicationContext类进行扫描，并用于构建bean定义，初始化Spring容器。
public class TestConditian {

	@Bean
//	@Bean： @Configuration启动容器+@Bean注册Bean，实例化@Bean
	@ConditionalOnProperty(prefix="mengfei", value="test", havingValue="open")
//	依赖于配置文件中的mengfei.test = open配置，如果没有配置，则不会实例化student
	public Student student(){
		System.out.println("初始化Student------------------------------------");
		return new Student();
	}
	
	@Bean
//	@ConditionalOnClass(value=TestConditi.class)
	@ConditionalOnBean(name="student2")
//	依赖于student2的注入，如果student2没有注入到容器中，就不会实例化student1
	public Student student1(){
		System.out.println("初始化Student1-----------------------------------");
		return new Student();
	}
}



//@Configuration
//public class TestConditi {
//
//	@Bean
//	public Student student2(){
//		System.out.println("实例化student2------------------------------");
//		return new Student();
//	}
//	
//	@Bean
//	public Student student3(){
//		System.out.println("实例化student3------------------------------");
//		return new Student();
//	}
//}
