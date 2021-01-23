package com.example.demo.excelEntity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

public class ExcelSegmentConnectPropertyIndexModel extends BaseRowModel {
	
    @ExcelProperty(value = "startImei" ,index = 0)
    private String startImei;

    @ExcelProperty(value = "endImei",index = 1)
    private String endImei;
    
    @ExcelProperty(value = "连续号段区间" ,index = 2)
    private String continueSegment;
    
//    @ExcelProperty(value = "间隔号段区间" ,index = 3)
//    private int blankSegment;

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

	public String getContinueSegment() {
		return continueSegment;
	}

	public void setContinueSegment(String continueSegment) {
		this.continueSegment = continueSegment;
	}

//	public int getBlankSegment() {
//		return blankSegment;
//	}
//
//	public void setBlankSegment(int blankSegment) {
//		this.blankSegment = blankSegment;
//	}

	@Override
	public String toString() {
		return "ExcelSegmentConnectPropertyIndexModel [startImei=" + startImei + ", endImei=" + endImei
				+ ", continueSegment=" + continueSegment + "]";
	}
    
}
