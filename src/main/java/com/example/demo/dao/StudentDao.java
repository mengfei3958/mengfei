package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import com.example.demo.entity.Student1;

public interface StudentDao extends CrudRepository<Student1,Integer>, JpaSpecificationExecutor{

}
