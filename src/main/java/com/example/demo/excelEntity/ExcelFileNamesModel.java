package com.example.demo.excelEntity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

public class ExcelFileNamesModel extends BaseRowModel{
	
	@ExcelProperty(value = "fileName",index = 0)
    private String fileName;
	
	@ExcelProperty(value = "size",index = 1)
    private int size;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	@Override
	public String toString() {
		return "ExcelFileNames [fileName=" + fileName + ", size=" + size + "]";
	}
	
	

}
