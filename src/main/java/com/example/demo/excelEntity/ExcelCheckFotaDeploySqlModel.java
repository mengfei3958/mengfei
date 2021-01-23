package com.example.demo.excelEntity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

public class ExcelCheckFotaDeploySqlModel extends BaseRowModel {

 @ExcelProperty(value = "meidSelectSql" ,index = 0)
    private String meidSelectSql;
 
 @ExcelProperty(value = "diffpackageSqls" ,index = 1)
    private String diffpackageSqls;
 
 @ExcelProperty(value = "forceUpdateRuleSql" ,index = 2)
    private String forceUpdateRuleSql;

 public String getMeidSelectSql() {
  return meidSelectSql;
 }

 public void setMeidSelectSql(String meidSelectSql) {
  this.meidSelectSql = meidSelectSql;
 }

 public String getDiffpackageSqls() {
  return diffpackageSqls;
 }

 public void setDiffpackageSqls(String diffpackageSqls) {
  this.diffpackageSqls = diffpackageSqls;
 }

 public String getForceUpdateRuleSql() {
  return forceUpdateRuleSql;
 }

 public void setForceUpdateRuleSql(String forceUpdateRuleSql) {
  this.forceUpdateRuleSql = forceUpdateRuleSql;
 }

 @Override
 public String toString() {
  return "CheckFotaDeploySqlModel [meidSelectSql=" + meidSelectSql + ", diffpackageSqls=" + diffpackageSqls
    + ", forceUpdateRuleSql=" + forceUpdateRuleSql + "]";
 }
 
 
}