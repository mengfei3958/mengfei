package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.baomidou.mybatisplus.annotations.TableName;

@Entity
@TableName("bank")
public class Bank {

	@Id
	@GeneratedValue
	@Column(name = "b_id")
	private int b_id;
	@Column(name = "name")
	private String bankName;

	public int getbId() {
		return b_id;
	}
	public void setbId(int bId) {
		this.b_id = bId;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	@Override
	public String toString() {
		return "Bank [bId=" + b_id + ", bankName=" + bankName + "]";
	}
}
