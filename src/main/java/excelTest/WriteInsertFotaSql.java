package excelTest;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.example.demo.excelEntity.ExcelInsertFotaSqlModel;
import com.example.demo.excelEntity.ExcelSegmentConnectPropertyIndexModel;

/**
 * 根据SD2500_imei.xlsx号段区间,把提取出的imei号段批量生成号段管理、差分包关联、强制升级规则的sql插入语句，生成writeInsertFotaSql.xlsx
 * @author 0193000683
 *
 */
public class WriteInsertFotaSql {
	
////	号段文件位置以及文件名称
//	private static final String FILE_PATH = "D:\\mengfei\\维护\\销售weFota导入\\红蜻蜓\\";
////	号段文件名称
//	public static final String FILE_NAME = "SD6200_imei.xlsx";
//	
////	写入文件位置
//	private static final String WRITE_FILE_PATH = "D:\\mengfei\\维护\\销售weFota导入\\红蜻蜓\\";
////	写入文件名称
//	private static final String WRITE_FILE_NAME = "writeInsertFotaSql.xlsx";
//	
////	产品名称
//	private static final String PRODUCT_NAME = "GM510";	
////	差分包名称
//	private static final String PACKAGE_NAME = "GM510C1AV1.1B05-360_GM510C1AV1.0B01";	
////	起始版本名称
//	public static final String ORIGIN_VERSION_NAME = "GM510C1AV1.1B05";	
////	目标版本名称
//	public static final String DEST_VERSION_NAME = "360_GM510C1AV1.0B01";
////	强制升级间隔时间
//	private static final String FORCE_UPDATE_RULE_INTERNAL_TIME = "24";
////	强制升级标识,1代表是，0代表否
//	private static final String FORCE_UPDATE_RULE_UPGRADE_LOGO = "0";
	
//	插入时间
	public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	
	public static final String CREATE_TIME = simpleDateFormat.format(new Date());
	
// 提取原始文件中的起始号段和结束号段
	private List<ExcelSegmentConnectPropertyIndexModel> excelSegmentConnectPropertyIndexModels = new ArrayList<>();
	
//	拼接插入号段sql
	private static final String insertMeidSelectSqls[] = {
		"insert into meidselect (istart, iend, icount, bupdate, updateversionId, productId, ipo, "
		+ "itime, rulestart, ruleend, user) SELECT '###############",
		"', '###############",
		"', NULL, 0, NULL, id, NULL, '" + CREATE_TIME
		+ "' , NULL, NULL, ',1,' FROM product where name = '",
		"';"
	};
//	拼接插入关联差分包sql
	private static final String insertMeidDiffSqls[] = {
		"INSERT INTO meiddiff (meidid, diffid) SELECT m.id, d.id from meidselect m, diffpackage d "
		+ "where m.istart = '###############",
		"' and d.name = '",
		"';"
	};
//	拼接插入强制升级规则sql
	private static final String insertForceUpdateMessageSqls[] = {
		"INSERT INTO force_update_rule (startSection, endSection, originVersion, destVersion, "
		+ "intervals, delay, flag, createTime) SELECT '###############",
		"', '###############",
		"', '",
		"', '",
		"', ",
		", 0, ",
		", '" + CREATE_TIME + "';"
	};
		
	
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
		OutputStream out = new FileOutputStream(ImeiConstant.WRITE_FILE_PATH + ImeiConstant.WRITE_INSERT_FOTA_SQL_FILE_NAME);
		try {
			ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX);
			Sheet sheet = new Sheet(1, 0, ExcelInsertFotaSqlModel.class);
			List<ExcelInsertFotaSqlModel> datasResult = new ArrayList<>();
			ExcelInsertFotaSqlModel fotaSqlModel;
			StringBuffer meidSelectBuffer;
			StringBuffer meidDiffBuffer;
			StringBuffer forceUpdateMessageBuffer;
			String startImei;
			String endImei;
			for (ExcelSegmentConnectPropertyIndexModel segment : excelSegmentConnectPropertyIndexModels) {
				startImei = segment.getStartImei();
				endImei = segment.getEndImei();
				fotaSqlModel = new ExcelInsertFotaSqlModel();
				meidSelectBuffer = new StringBuffer();
				meidDiffBuffer = new StringBuffer();
				forceUpdateMessageBuffer = new StringBuffer();
//				拼接插入号段sql
				meidSelectBuffer.append(insertMeidSelectSqls[0]);
				meidSelectBuffer.append(startImei);
				meidSelectBuffer.append(insertMeidSelectSqls[1]);
				meidSelectBuffer.append(endImei);
				meidSelectBuffer.append(insertMeidSelectSqls[2]);
				meidSelectBuffer.append(ImeiConstant.PRODUCT_NAME);
				meidSelectBuffer.append(insertMeidSelectSqls[3]);
				fotaSqlModel.setInsertMeidSelectSqls(meidSelectBuffer.toString());
//				拼接插入关联差分包sql
				meidDiffBuffer.append(insertMeidDiffSqls[0]);
				meidDiffBuffer.append(startImei);
				meidDiffBuffer.append(insertMeidDiffSqls[1]);
				meidDiffBuffer.append(ImeiConstant.PACKAGE_NAME);
				meidDiffBuffer.append(insertMeidDiffSqls[2]);
				fotaSqlModel.setInsertMeidDiffSqls(meidDiffBuffer.toString());
//				拼接插入强制升级规则sql
				forceUpdateMessageBuffer.append(insertForceUpdateMessageSqls[0]);
				forceUpdateMessageBuffer.append(startImei);
				forceUpdateMessageBuffer.append(insertForceUpdateMessageSqls[1]);
				forceUpdateMessageBuffer.append(endImei);
				forceUpdateMessageBuffer.append(insertForceUpdateMessageSqls[2]);
				forceUpdateMessageBuffer.append(ImeiConstant.ORIGIN_VERSION_NAME);
				forceUpdateMessageBuffer.append(insertForceUpdateMessageSqls[3]);
				forceUpdateMessageBuffer.append(ImeiConstant.DEST_VERSION_NAME);
				forceUpdateMessageBuffer.append(insertForceUpdateMessageSqls[4]);
				forceUpdateMessageBuffer.append(ImeiConstant.FORCE_UPDATE_RULE_INTERNAL_TIME);
				forceUpdateMessageBuffer.append(insertForceUpdateMessageSqls[5]);
				forceUpdateMessageBuffer.append(ImeiConstant.FORCE_UPDATE_RULE_UPGRADE_LOGO);
				forceUpdateMessageBuffer.append(insertForceUpdateMessageSqls[6]);
				fotaSqlModel.setInsertForceUpdateMessageSqls(forceUpdateMessageBuffer.toString());
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
