package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.baomidou.mybatisplus.annotations.TableField;
import com.github.crab2died.annotation.ExcelField;


//import top.ibase4j.core.base.BaseModel;

@Entity
@Table(name="student")
public class Student1{

	@ExcelField(title = "学号", order = 1)
	@Id
	@GeneratedValue
	private int id1;
	@ExcelField(title = "姓名", order = 2)
	private String name;
	//@JoinColumn(name = "foreig")定义Student表的外键，bank关联外键是其主键
	@ManyToOne
    @JoinColumn(name = "b_id")
	private Bank bank;	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId1() {
		return id1;
	}
	public void setId(int id) {
		this.id1 = id;
	}

	public Bank getBank() {
		return bank;
	}
	public void setBank(Bank bank) {
		this.bank = bank;
	}
	@Override
	public String toString() {
		return "Student [name=" + name + ", id=" + id1 + "]";
	}
	
	private void getStu(int stuName){
		System.out.println("获取学生信息" + stuName);
	}
		
}
