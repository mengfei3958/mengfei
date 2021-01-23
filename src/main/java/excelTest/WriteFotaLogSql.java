package excelTest;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.example.demo.excelEntity.ExcelSegmentConnectPropertyIndexModel;
import com.example.demo.excelEntity.ExcelFotaLogSqlModel;

/**
 * 根据SD2500_imei.xlsx号段区间生成每日工作日报的sql查询语句，生成writeFotaLogSql.xlsx
 * @author 0193000683
 *
 */
public class WriteFotaLogSql {
	
////	号段文件位置以及文件名称
//	private static final String FILE_PATH = "D:\\mengfei\\维护\\SD6200秦凯20200224\\检查配置规则以及生成查询日志sql\\";
////	号段文件名称
//	private static final String FILE_NAME = "SD6200_imei.xlsx";
//	
////	写入文件位置
//	private static final String WRITE_FILE_PATH = "D:\\mengfei\\维护\\SD6200秦凯20200224\\检查配置规则以及生成查询日志sql\\";
////	写入文件名称
//	private static final String WRITE_FILE_NAME = "writeFotaLogSql.xlsx";
//	
////	起始版本名称
//	private static final String ORIGIN_VERSION = "EN_SD6200V1.0.0B01"; 

// 提取原始文件中的起始号段和结束号段
	private List<ExcelSegmentConnectPropertyIndexModel> excelSegmentConnectPropertyIndexModels = new ArrayList<>();
	
//	查询升级结果
	private static final String updateResultSqls[] = {
		"select DISTINCT devId from diffpack_update_result where devId BETWEEN '###############", 
		"' and '###############",
		"' and originVersion = '",
		"' and createTime BETWEEN '2020-02-26 00:00:00' and '2020-02-27 00:00:00' and result = '1' and devid not in "
		+ "(select DISTINCT devId from diffpack_update_result where devId BETWEEN '###############",
		"' and '###############",
		"' and originVersion = '",
		"' and createTime BETWEEN '2020-02-10 00:00:00' and '2020-02-26 00:00:00' and result = '1')",
		" UNION ALL"
	};
	
//	查询目标版本
	private static final String forceUpdateMessageSqls[] = {
		"select DISTINCT devId  from force_update_message where originVersion = '", 
		"' and devid BETWEEN '###############",
		"' and '###############",
		"' and createTime BETWEEN '2020-02-26 00:00:00' and '2020-02-27 00:00:00'  and devid not in "
		+ "(select DISTINCT devId  from force_update_message where originVersion = '",
		"' and devid BETWEEN '###############",
		"' and '###############",
		"' and createTime BETWEEN '2020-02-10 00:00:00' and '2020-02-26 00:00:00' )",
		" UNION ALL"
	};

//	查询已下载差分包
	private static final String diffupdateSqls[] = {
		"select DISTINCT imeimeid  from diffupdate where imeimeid BETWEEN '###############", 
		"' and '###############",
		"' and updateTime BETWEEN '2020-02-26 00:00:00' and '2020-02-27 00:00:00' and success = '1' and imeimeid not in "
		+ "(select DISTINCT imeimeid  from diffupdate where imeimeid BETWEEN '###############",
		"' and '###############",
		"' and updateTime BETWEEN '2020-02-10 00:00:00' and '2020-02-26 00:00:00' and success = '1')",
		" UNION ALL"
	};


//	 生成查询已下载差分包，返回结果以及查询目标版本sql
	@Test
	public void excelRead() throws Exception {
		InputStream inputStream = new BufferedInputStream(
				new FileInputStream(ImeiConstant.FILE_PATH + ImeiConstant.MEIDSELECT_FILE_NAME));
		@SuppressWarnings("rawtypes")
		AnalysisEventListener listener = new AnalysisEventListener() {
			@Override
			public void invoke(Object object, AnalysisContext context) {
				// TODO Auto-generated method stub
				// 加载表格数据
				excelSegmentConnectPropertyIndexModels.add((ExcelSegmentConnectPropertyIndexModel) object);
			}
			@Override
			public void doAfterAllAnalysed(AnalysisContext paramAnalysisContext) {
				// TODO Auto-generated method stub
			}
		};
		ExcelReader excelReader = new ExcelReader(inputStream, null, listener);
		excelReader.read(new Sheet(1, 1, ExcelSegmentConnectPropertyIndexModel.class));
		excelWriteOfFotaSql();
	}

	public void excelWriteOfFotaSql() throws FileNotFoundException {
		OutputStream out = new FileOutputStream(ImeiConstant.WRITE_FILE_PATH + ImeiConstant.WRITE_FOTA_LOG_SQL_FILE_NAME);
		try {
			ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX);
			Sheet sheet = new Sheet(1, 0, ExcelFotaLogSqlModel.class);
			List<ExcelFotaLogSqlModel> datasResult = new ArrayList<>();
			ExcelFotaLogSqlModel fotaSqlModel;
			StringBuffer resultStringBuffer;
			StringBuffer versionStringBuffer;
			StringBuffer packageStringBuffer;
			String startImei;
			String endImei;
			int segmentSize = excelSegmentConnectPropertyIndexModels.size();
			for (ExcelSegmentConnectPropertyIndexModel segment : excelSegmentConnectPropertyIndexModels) {
				startImei = segment.getStartImei();
				endImei = segment.getEndImei();
				fotaSqlModel = new ExcelFotaLogSqlModel();
				resultStringBuffer = new StringBuffer();
				versionStringBuffer = new StringBuffer();
				packageStringBuffer = new StringBuffer();
//				拼接升级结果sql
				resultStringBuffer.append(updateResultSqls[0]);
				resultStringBuffer.append(startImei);
				resultStringBuffer.append(updateResultSqls[1]);
				resultStringBuffer.append(endImei);
				resultStringBuffer.append(updateResultSqls[2]);
				resultStringBuffer.append(ImeiConstant.ORIGIN_VERSION_NAME);
				resultStringBuffer.append(updateResultSqls[3]);
				resultStringBuffer.append(startImei);
				resultStringBuffer.append(updateResultSqls[4]);
				resultStringBuffer.append(endImei);
				resultStringBuffer.append(updateResultSqls[5]);
				resultStringBuffer.append(ImeiConstant.ORIGIN_VERSION_NAME);
				resultStringBuffer.append(updateResultSqls[6]);
				if(segment != excelSegmentConnectPropertyIndexModels.get(segmentSize - 1)){
					resultStringBuffer.append(updateResultSqls[7]);
				}
				fotaSqlModel.setUpdateResultSqls(resultStringBuffer.toString());
//			拼接目标版本sql
				versionStringBuffer.append(forceUpdateMessageSqls[0]);
				versionStringBuffer.append(ImeiConstant.ORIGIN_VERSION_NAME);
				versionStringBuffer.append(forceUpdateMessageSqls[1]);
				versionStringBuffer.append(startImei);
				versionStringBuffer.append(forceUpdateMessageSqls[2]);
				versionStringBuffer.append(endImei);
				versionStringBuffer.append(forceUpdateMessageSqls[3]);
				versionStringBuffer.append(ImeiConstant.ORIGIN_VERSION_NAME);
				versionStringBuffer.append(forceUpdateMessageSqls[4]);
				versionStringBuffer.append(startImei);
				versionStringBuffer.append(forceUpdateMessageSqls[5]);
				versionStringBuffer.append(endImei);
				versionStringBuffer.append(forceUpdateMessageSqls[6]);
				if(segment != excelSegmentConnectPropertyIndexModels.get(segmentSize - 1)){
					versionStringBuffer.append(forceUpdateMessageSqls[7]);
				}
				fotaSqlModel.setForceUpdateMessageSqls(versionStringBuffer.toString());
//			拼接已下载差分包sql
				packageStringBuffer.append(diffupdateSqls[0]);
				packageStringBuffer.append(startImei);
				packageStringBuffer.append(diffupdateSqls[1]);
				packageStringBuffer.append(endImei);
				packageStringBuffer.append(diffupdateSqls[2]);
				packageStringBuffer.append(startImei);
				packageStringBuffer.append(diffupdateSqls[3]);
				packageStringBuffer.append(endImei);
				packageStringBuffer.append(diffupdateSqls[4]);
				if(segment != excelSegmentConnectPropertyIndexModels.get(segmentSize - 1)){
					packageStringBuffer.append(diffupdateSqls[5]);
				}
				fotaSqlModel.setDiffupdateSqls(packageStringBuffer.toString());
				datasResult.add(fotaSqlModel);
			}
			writer.write(datasResult, sheet);
			writer.finish();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
