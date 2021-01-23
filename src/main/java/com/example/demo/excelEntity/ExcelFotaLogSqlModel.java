package com.example.demo.excelEntity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

public class ExcelFotaLogSqlModel extends BaseRowModel {

	@ExcelProperty(value = "diffupdateSqls" ,index = 0)
    private String diffupdateSqls;
	
	@ExcelProperty(value = "updateResultSqls" ,index = 1)
    private String updateResultSqls;
	
	@ExcelProperty(value = "forceUpdateMessageSqls" ,index = 2)
    private String forceUpdateMessageSqls;

	public String getDiffupdateSqls() {
		return diffupdateSqls;
	}

	public void setDiffupdateSqls(String diffupdateSqls) {
		this.diffupdateSqls = diffupdateSqls;
	}

	public String getUpdateResultSqls() {
		return updateResultSqls;
	}

	public void setUpdateResultSqls(String updateResultSqls) {
		this.updateResultSqls = updateResultSqls;
	}

	public String getForceUpdateMessageSqls() {
		return forceUpdateMessageSqls;
	}

	public void setForceUpdateMessageSqls(String forceUpdateMessageSqls) {
		this.forceUpdateMessageSqls = forceUpdateMessageSqls;
	}

	@Override
	public String toString() {
		return "FotaLogSqlModel [diffupdateSqls=" + diffupdateSqls + ", updateResultSqls=" + updateResultSqls
				+ ", forceUpdateMessageSqls=" + forceUpdateMessageSqls + "]";
	}

	
}
