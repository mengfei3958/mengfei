package com.example.demo.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

public class DataLink extends BaseRowModel{
	
	@ExcelProperty(value = "startImei" ,index = 0)
    private String startImei;
	
	@ExcelProperty(value = "endImei" ,index = 1)
    private String endImei;

	public String getStartImei() {
		return startImei;
	}

	public void setStartImei(String startImei) {
		this.startImei = startImei;
	}

	public String getEndImei() {
		return endImei;
	}

	public void setEndImei(String endImei) {
		this.endImei = endImei;
	}
	
	
}
