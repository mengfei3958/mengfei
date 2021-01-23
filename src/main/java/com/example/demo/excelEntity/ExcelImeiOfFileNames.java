package com.example.demo.excelEntity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

public class ExcelImeiOfFileNames extends BaseRowModel {
	
//	索引序号从0开始
	@ExcelProperty(value = "IMEI",index = 9)
    private String imei;
    

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	@Override
	public String toString() {
		return "ExcelFileNames [imei=" + imei + "]";
	}


}
