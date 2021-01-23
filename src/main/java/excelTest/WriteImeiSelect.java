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
import java.util.ListIterator;

import org.junit.Test;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.example.demo.excelEntity.ExcelImeiSelectModel;

/**
 * 根据imei.xlsx文件提取出号段列表，包括连续的和所有不连续的imei号，生成SD6200_imei.xlsx
 * @author 0193000683
 *
 */
public class WriteImeiSelect {

	
//	号段按照升序排序后，提取出连续imei的数量大于2的所有号段区间以及独立的imei号
	private List<ExcelImeiSelectModel> datasSegmentation = new ArrayList<>();
	
////	imei号原始数据的文件位置
//	private static final String FILE_PATH = "D:\\mengfei\\维护\\销售weFota导入\\红蜻蜓\\";	
////	imei号原始数据的文件名称
//	private static final String FILE_NAME = "imei.xlsx";
//	
////	写入号段的文件位置
//	private static final String WRITE_IMEI_SELECT_PATH = "D:\\mengfei\\维护\\销售weFota导入\\红蜻蜓\\";	
////	写入号段的文件名称
//	private static final String WRITE_IMEI_SELECT_NAME = "SD6200_imei.xlsx";

	
//	号段按照升序排序后，提取出连续imei的数量大于2的所有号段区间
	@Test
	public void excelReadOfSegmentation() throws Exception {
		InputStream inputStream = new BufferedInputStream(new FileInputStream(ImeiConstant.FILE_PATH + ImeiConstant.IMEI_FILE_NAME));
		@SuppressWarnings("rawtypes")
		AnalysisEventListener listener = new AnalysisEventListener(){

			@Override
			public void invoke(Object object, AnalysisContext context) {
				// TODO Auto-generated method stub
//				加载表格数据
				datasSegmentation.add((ExcelImeiSelectModel) object);
			}

			@Override
			public void doAfterAllAnalysed(AnalysisContext paramAnalysisContext) {
				// TODO Auto-generated method stub
			}};
		ExcelReader excelReader = new ExcelReader(inputStream, null, listener);
		excelReader.read(new Sheet(1, 1, ExcelImeiSelectModel.class));
		excelWriteOfSegmentation();
	}


//    号段分割写入数据
    public void excelWriteOfSegmentation() throws FileNotFoundException {
        OutputStream out = new FileOutputStream(ImeiConstant.WRITE_FILE_PATH + ImeiConstant.MEIDSELECT_FILE_NAME);
        try {
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX);
            //写第一个sheet, sheet1  数据全是List<ExcelPropertyIndexModel> 模型映射关系
            Sheet sheet = new Sheet(1, 0, ExcelImeiSelectModel.class);
            List<ExcelImeiSelectModel> datasResult = new ArrayList<>();
            long imeiStart = 0;
    		int size;
    		long imeiEnd = 0;
    		String imei;
    		long tempImeiNextReverseLong;
    		long tempImeiForwardReverseLong;
    		for (int i = 0; i < datasSegmentation.size(); i++) {
    			imeiStart = Long.parseLong(datasSegmentation.get(i).getStartImei().
    					substring(0, datasSegmentation.get(i).getStartImei().length() - 1));
    			size = 0;
//    			System.out.println("imeiStart == " + imeiStart);
    			if(i == 0){
    				tempImeiNextReverseLong = Long.parseLong(datasSegmentation.get(i + 1).getStartImei().
        					substring(0, datasSegmentation.get(i + 1).getStartImei().length() - 1));
    				if(imeiStart != tempImeiNextReverseLong - 1){
    					datasSegmentation.get(i).setSize(1);
    					datasSegmentation.get(i).setEndImei(datasSegmentation.get(i).getStartImei());
    					datasResult.add(datasSegmentation.get(i));
    					continue;
    				}
    			} else if(i != 0 && i != datasSegmentation.size() - 1){
    				tempImeiForwardReverseLong = Long.parseLong(datasSegmentation.get(i - 1).getStartImei().
        					substring(0, datasSegmentation.get(i -1).getStartImei().length() - 1));
        			tempImeiNextReverseLong = Long.parseLong(datasSegmentation.get(i + 1).getStartImei().
        					substring(0, datasSegmentation.get(i + 1).getStartImei().length() - 1));
    				if((imeiStart != tempImeiForwardReverseLong + 1) &&(imeiStart != tempImeiNextReverseLong - 1)){
//    					添加前后都不连续的imei号
    					datasSegmentation.get(i).setSize(1);
    					datasSegmentation.get(i).setEndImei(datasSegmentation.get(i).getStartImei());
        				datasResult.add(datasSegmentation.get(i));
    					continue;
    				}
    			} else if(i == datasSegmentation.size() - 1){
    				tempImeiForwardReverseLong = Long.parseLong(datasSegmentation.get(i - 1).getStartImei().
        					substring(0, datasSegmentation.get(i -1).getStartImei().length() - 1));
    				if(imeiStart != tempImeiForwardReverseLong + 1){
    					datasSegmentation.get(i).setSize(1);
    					datasSegmentation.get(i).setEndImei(datasSegmentation.get(i).getStartImei());
    					datasResult.add(datasSegmentation.get(i));
    					continue;
    				}
    			}
//    			从for循环的下一条数据开始做比较
    			ListIterator<ExcelImeiSelectModel> rs = datasSegmentation.listIterator(i + 1);
    			while(rs.hasNext()){
    				imei = rs.next().getStartImei();
    				imeiEnd = Long.parseLong(imei.substring(0, imei.length() - 1));
//    				System.out.println("imeiEnd == " + imeiEnd);
    				if(imeiStart == imeiEnd - 1){
    					size++;
    					i++;
    					imeiStart++;
//    					数字连续则继续比较下一条数据；
    					continue;
    				}
//    				出现不连续的数据就退出while循环，继续下一个for循环的数据，节省时间
    				break;				
    			}
//    			如果两个以上的数字连续，则记录
    			if(size >= 1){
//    				System.out.println("startImei row number == " + (i - size + 1));
    				datasSegmentation.get(i - size).setSize(size + 1);//连续的数据条数求和
    				datasSegmentation.get(i - size).setEndImei(datasSegmentation.get(i).getStartImei());
    				datasResult.add(datasSegmentation.get(i - size));
    			}			
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
