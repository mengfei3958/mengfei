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
import com.example.demo.excelEntity.ExcelCheckFotaDeploySqlModel;
import com.example.demo.excelEntity.ExcelImeiSelectModel;

/**
 * 针对单个imei号，只使用对象ExcelImeiSelectModel的imei字段
 * 根据SD2500_imei.xlsx号段区间检查已部署的号段管理、差分包关联、强制升级规则的sql插入结果的正确性，生成checkWriteInsertFotaSqlByImei.xlsx
 * @author 0193000683
 *
 */
public class CheckFotaDeploySqlByImei {
	
//	号段文件位置
	private static final String FILE_PATH = "D:\\mengfei\\维护\\张洪明\\";
	
//	号段文件名称
	private static final String FILE_NAME = "imei_B09.xlsx";
	
//	写入文件位置
	private static final String WRITE_FILE_PATH = "D:\\mengfei\\维护\\张洪明\\";
	
//	写入文件名称
	private static final String WRITE_FILE_NAME = "checkWriteInsertFotaSqlByImei_B09.xlsx";
	
	// 提取原始文件中的起始号段和结束号段
		private List<ExcelImeiSelectModel> excelImeiSelectModels = new ArrayList<>();
		
////		原始版本名称
		private static final String ORIGIN_VERSION = "TMO_US_SD2000V1.0.0B16";
//		
////		目标版本名称
		private static final String DEST_VERSION = "TMO_US_SD2000V1.0.0B28";
	
//		检验号段区间
		private static final String meidSelectSql[] = {
				"select istart from meidselect where '###############", 
				"' between istart and iend",
				" UNION ALL"
		};
		
//		检验强制升级规则
		private static final String forceUpdateRuleSql[] = {
				"select startSection from force_update_rule where '###############", 
				"' between startSection and endSection",
				" and originVersion = '",
				"' and destVersion = '",
				"'",
				" UNION ALL"
		};
		
//		检验关联差分包
		private static final String diffpackageSqls[] = {
				"select v.`name` AS originVersionName, df.`name` AS packageName from diffpackage df LEFT JOIN meiddiff md"
				+ " ON df.id = md.diffid LEFT JOIN meidselect ms ON md.meidid = ms.id", 
				" LEFT JOIN version v ON v.id = df.originVersionId WHERE '###############",
				"' between istart and iend",
				" AND v.`name` = '",
				"'",
				" UNION ALL"
		};
		
//		生成检验号段，升级规则，关联差分包sql
		@Test
		public void excelRead() throws Exception {
			InputStream inputStream = new BufferedInputStream(
					new FileInputStream(FILE_PATH + FILE_NAME));
			@SuppressWarnings("rawtypes")
			AnalysisEventListener listener = new AnalysisEventListener() {

				@Override
				public void invoke(Object object, AnalysisContext context) {
					// TODO Auto-generated method stub
					// 加载表格数据
					excelImeiSelectModels.add((ExcelImeiSelectModel) object);
				}

				@Override
				public void doAfterAllAnalysed(AnalysisContext paramAnalysisContext) {
					// TODO Auto-generated method stub
				}
			};
			ExcelReader excelReader = new ExcelReader(inputStream, null, listener);
			excelReader.read(new Sheet(1, 1, ExcelImeiSelectModel.class));
			excelWriteOfFotaSql();
		}

		public void excelWriteOfFotaSql() throws FileNotFoundException {
			OutputStream out = new FileOutputStream(WRITE_FILE_PATH + WRITE_FILE_NAME);
			try {
				ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX);
				Sheet sheet = new Sheet(1, 0, ExcelCheckFotaDeploySqlModel.class);
				List<ExcelCheckFotaDeploySqlModel> datasResult = new ArrayList<>();
				ExcelCheckFotaDeploySqlModel fotaSqlModel;
				StringBuffer meidSelectStringBuffer;
				StringBuffer diffpackageSqlsStringBuffer;
				StringBuffer forceUpdateRuleSqlStringBuffer;
				String imei;
				int segmentSize = excelImeiSelectModels.size();
				for (ExcelImeiSelectModel segment : excelImeiSelectModels) {
					imei = segment.getStartImei();
					fotaSqlModel = new ExcelCheckFotaDeploySqlModel();
//					拼接号段管理sql
					meidSelectStringBuffer = new StringBuffer();
					diffpackageSqlsStringBuffer = new StringBuffer();
					forceUpdateRuleSqlStringBuffer = new StringBuffer();
					meidSelectStringBuffer.append(meidSelectSql[0]);
					meidSelectStringBuffer.append(imei);
					meidSelectStringBuffer.append(meidSelectSql[1]);
					if(segment != excelImeiSelectModels.get(segmentSize - 1)){
						meidSelectStringBuffer.append(meidSelectSql[2]);
					}
					fotaSqlModel.setMeidSelectSql(meidSelectStringBuffer.toString());
//				拼接强制升级规则sql
					forceUpdateRuleSqlStringBuffer.append(forceUpdateRuleSql[0]);
					forceUpdateRuleSqlStringBuffer.append(imei);
					forceUpdateRuleSqlStringBuffer.append(forceUpdateRuleSql[1]);
					forceUpdateRuleSqlStringBuffer.append(forceUpdateRuleSql[2]);
					forceUpdateRuleSqlStringBuffer.append(ORIGIN_VERSION);
					forceUpdateRuleSqlStringBuffer.append(forceUpdateRuleSql[3]);
					forceUpdateRuleSqlStringBuffer.append(DEST_VERSION);
					forceUpdateRuleSqlStringBuffer.append(forceUpdateRuleSql[4]);
					if(segment != excelImeiSelectModels.get(segmentSize - 1)){
						forceUpdateRuleSqlStringBuffer.append(forceUpdateRuleSql[5]);
					}
					fotaSqlModel.setForceUpdateRuleSql(forceUpdateRuleSqlStringBuffer.toString());
//				拼接差分包sql
					diffpackageSqlsStringBuffer.append(diffpackageSqls[0]);
					diffpackageSqlsStringBuffer.append(diffpackageSqls[1]);
					diffpackageSqlsStringBuffer.append(imei);
					diffpackageSqlsStringBuffer.append(diffpackageSqls[2]);
					diffpackageSqlsStringBuffer.append(diffpackageSqls[3]);
					diffpackageSqlsStringBuffer.append(ORIGIN_VERSION);
					diffpackageSqlsStringBuffer.append(diffpackageSqls[4]);
					if(segment != excelImeiSelectModels.get(segmentSize - 1)){
						diffpackageSqlsStringBuffer.append(diffpackageSqls[5]);
					}
					fotaSqlModel.setDiffpackageSqls(diffpackageSqlsStringBuffer.toString());
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
