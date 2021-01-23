package com.example.demo.excelEntity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

public class ExcelSegmentPropertyIndexModel extends BaseRowModel {
	
    @ExcelProperty(value = "planOrderId" ,index = 0)
    private String planOrderId;

    @ExcelProperty(value = "imei",index = 1)
    private String imei;
    
    @ExcelProperty(value = "size" ,index = 2)
    private int size;
    
    @ExcelProperty(value = "id" ,index = 3)
    private long id;
    
	public String getPlanOrderId() {
		return planOrderId;
	}

	public void setPlanOrderId(String planOrderId) {
		this.planOrderId = planOrderId;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "ExcelPropertyIndexModelTest [planOrderId=" + planOrderId + ", imei=" + imei + ", size=" + size + "]";
	}
	   

}
