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

/**
 * 根据imei.xlsx文件提取出起始号段和结束号段两列的imei号并拼接在一个单元格里，作为第三列号段区间，生成writeSegment.xlsx
 * @author 0193000683
 *
 */
public class ImeiSelectConcat {

	// 将起始号段和结束号段拼接在一起   
		private List<ExcelSegmentConnectPropertyIndexModel> datasSegmentationConnect = new ArrayList<>();
	
	// 将起始号段和结束号段拼接在一起   
		@Test
		public void excelReadOfSegmentationConnect() throws Exception {
			InputStream inputStream = new BufferedInputStream(new FileInputStream("C:\\Users\\mengfei\\Desktop\\号段区间分割.xlsx"));
			@SuppressWarnings("rawtypes")
			AnalysisEventListener listener = new AnalysisEventListener(){

				@Override
				public void invoke(Object object, AnalysisContext context) {
					// TODO Auto-generated method stub
//					加载表格数据
					datasSegmentationConnect.add((ExcelSegmentConnectPropertyIndexModel) object);
				}

				@Override
				public void doAfterAllAnalysed(AnalysisContext paramAnalysisContext) {
					// TODO Auto-generated method stub
				}};
			ExcelReader excelReader = new ExcelReader(inputStream, null, listener);
			excelReader.read(new Sheet(1, 1, ExcelSegmentConnectPropertyIndexModel.class));
			excelWriteOfSegmentationConnect();
		}
		
		
	  public void excelWriteOfSegmentationConnect() throws FileNotFoundException {
	      OutputStream out = new FileOutputStream("C:\\Users\\mengfei\\Desktop\\writeSegment.xlsx");
	      try {
	          ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX);
	          //写第一个sheet, sheet1  数据全是List<ExcelPropertyIndexModel> 模型映射关系
	          Sheet sheet = new Sheet(1, 0, ExcelSegmentConnectPropertyIndexModel.class);
	          List<ExcelSegmentConnectPropertyIndexModel> datasResult = new ArrayList<>();
	          ExcelSegmentConnectPropertyIndexModel segment;
	          for (ExcelSegmentConnectPropertyIndexModel excelSegmentPropertyIndexModel : datasSegmentationConnect) {
	        	  segment = new ExcelSegmentConnectPropertyIndexModel();
	        	  segment.setStartImei(excelSegmentPropertyIndexModel.getStartImei());
	        	  segment.setEndImei(excelSegmentPropertyIndexModel.getEndImei());
	        	  segment.setContinueSegment(excelSegmentPropertyIndexModel.getStartImei() + "-" + excelSegmentPropertyIndexModel.getEndImei());
	        	  datasResult.add(segment);
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
