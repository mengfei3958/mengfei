package com.example.demo.excelEntity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

public class ExcelImeiSelectModel extends BaseRowModel{

	@ExcelProperty(value = "startImei",index = 0)
//	起始号段
    private String startImei;
	
	@ExcelProperty(value = "endImei",index = 1)
    private String endImei;
    
    @ExcelProperty(value = "size" ,index = 2)
    private int size;
    
    @ExcelProperty(value = "modelImei" ,index = 3)
    private String modelImei;

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

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getModelImei() {
		return modelImei;
	}

	public void setModelImei(String modelImei) {
		this.modelImei = modelImei;
	}

	@Override
	public String toString() {
		return "ExcelImeiSelectModel [imei=" + startImei + ", endImei=" + endImei + ", size=" + size + "]";
	}
       
}
