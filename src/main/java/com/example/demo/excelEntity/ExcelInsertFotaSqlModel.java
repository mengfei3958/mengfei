package com.example.demo.excelEntity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

public class ExcelInsertFotaSqlModel extends BaseRowModel {

	@ExcelProperty(value = "insertMeidSelectSqls" ,index = 0)
    private String insertMeidSelectSqls;
	
	@ExcelProperty(value = "insertMeidDiffSqls" ,index = 1)
    private String insertMeidDiffSqls;
	
	@ExcelProperty(value = "insertForceUpdateMessageSqls" ,index = 2)
    private String insertForceUpdateMessageSqls;

	public String getInsertMeidSelectSqls() {
		return insertMeidSelectSqls;
	}

	public void setInsertMeidSelectSqls(String insertMeidSelectSqls) {
		this.insertMeidSelectSqls = insertMeidSelectSqls;
	}

	public String getInsertMeidDiffSqls() {
		return insertMeidDiffSqls;
	}

	public void setInsertMeidDiffSqls(String insertMeidDiffSqls) {
		this.insertMeidDiffSqls = insertMeidDiffSqls;
	}

	public String getInsertForceUpdateMessageSqls() {
		return insertForceUpdateMessageSqls;
	}

	public void setInsertForceUpdateMessageSqls(String insertForceUpdateMessageSqls) {
		this.insertForceUpdateMessageSqls = insertForceUpdateMessageSqls;
	}

	@Override
	public String toString() {
		return "ExcelInsertFotaSqlModel [insertMeidSelectSqls=" + insertMeidSelectSqls + ", insertMeidDiffSqls="
				+ insertMeidDiffSqls + ", insertForceUpdateMessageSqls=" + insertForceUpdateMessageSqls + "]";
	}

	
	
}
