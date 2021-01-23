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
 * 根据imei.xlsx提取出号段列表，包括商用机和样机，并且把样机单独拆分出来，生成SD2500_imei.xlsx
 * @author 0193000683
 *
 */
public class ImeiSelectOfModel {

	
//	号段按照升序排序后，提取出连续imei的数量大于2的所有号段区间以及独立的imei号
	private List<ExcelImeiSelectModel> datasSegmentation = new ArrayList<>();
	
	private List<ExcelImeiSelectModel> result = new ArrayList<>();
	
//	imei号原始数据的文件位置
	private static final String FILE_PATH = "D:\\mengfei\\维护\\SD6500-任军\\检查配置规则以及生成查询日志sql\\";
	
//	imei号原始数据的文件名称
	private static final String FILE_NAME = "imei.xlsx";
	
//	写入号段的文件位置
	private static final String WRITE_IMEI_SELECT_PATH = "D:\\mengfei\\维护\\SD6500-任军\\检查配置规则以及生成查询日志sql\\";
	
//	写入号段的文件名称
	private static final String WRITE_IMEI_SELECT_NAME = "SD6500_imei.xlsx";

	
//	号段按照升序排t序后，提取出连续imei的数量大于2的所有号段区间
	@Test
	public void excelReadOfSegmentation() throws Exception {
		InputStream inputStream = new BufferedInputStream(new FileInputStream(FILE_PATH + FILE_NAME));
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
        OutputStream out = new FileOutputStream(WRITE_IMEI_SELECT_PATH + WRITE_IMEI_SELECT_NAME);
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
    		List<String> modelImeiList = new ArrayList<>();
    		for (int i = 0, imeiSize = datasSegmentation.size(); i < imeiSize; i++) {
    			imeiStart = Long.parseLong(datasSegmentation.get(i).getStartImei().
    					substring(0, datasSegmentation.get(i).getStartImei().length() - 1));
    			size = 0;
    			if(i == 0){
    				tempImeiNextReverseLong = Long.parseLong(datasSegmentation.get(i + 1).getStartImei().
        					substring(0, datasSegmentation.get(i + 1).getStartImei().length() - 1));
    				if(imeiStart != tempImeiNextReverseLong - 1){
    					datasSegmentation.get(i).setSize(1);
    					datasSegmentation.get(i).setEndImei(datasSegmentation.get(i).getStartImei());
    					datasResult.add(datasSegmentation.get(i));
    					if(datasSegmentation.get(i).getModelImei() != null){
        					modelImeiList.add(datasSegmentation.get(i).getModelImei());
        				}
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
        				if(datasSegmentation.get(i).getModelImei() != null){
        					modelImeiList.add(datasSegmentation.get(i).getModelImei());
        				}
        				continue;
    				}
    			} else if(i == datasSegmentation.size() - 1){
    				tempImeiForwardReverseLong = Long.parseLong(datasSegmentation.get(i - 1).getStartImei().
        					substring(0, datasSegmentation.get(i -1).getStartImei().length() - 1));
    				if(imeiStart != tempImeiForwardReverseLong + 1){
    					datasSegmentation.get(i).setSize(1);
    					datasSegmentation.get(i).setEndImei(datasSegmentation.get(i).getStartImei());
    					datasResult.add(datasSegmentation.get(i));
    					if(datasSegmentation.get(i).getModelImei() != null){
        					modelImeiList.add(datasSegmentation.get(i).getModelImei());
        				}
    					continue;
    				}
    			}
//    			从for循环的下一条数据开始做比较
    			ListIterator<ExcelImeiSelectModel> rs = datasSegmentation.listIterator(i + 1);
    			while(rs.hasNext()){
    				imei = rs.next().getStartImei();
    				imeiEnd = Long.parseLong(imei.substring(0, imei.length() - 1));
    				if(datasSegmentation.get(i).getModelImei() != null){
    					modelImeiList.add(datasSegmentation.get(i).getModelImei());
    				}
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
    		System.out.println(modelImeiList.size());
    		if(modelImeiList.size() > 0){
        		imeiDevision(datasResult, modelImeiList);
        		writer.write(result, sheet);
    		} else {
    			writer.write(datasResult, sheet);
    		}
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
    
    public void imeiDevision(List<ExcelImeiSelectModel> datasResult, List<String> modelImeiList){
    	String startImei;
    	String endImei;
    	long startImeiReverseLong;
    	long endImeiReverseLong;
    	long modelImeiReverseLong;
    	ExcelImeiSelectModel tempImeiModel;
    	List<ExcelImeiSelectModel> tempRecicle;
    	int size;
    	int devisionSize;
    	String modelImei;
    	for(ExcelImeiSelectModel imeiSelectModel: datasResult){
    		startImei = imeiSelectModel.getStartImei();
    		endImei = imeiSelectModel.getEndImei();
    		size = imeiSelectModel.getSize();
			startImeiReverseLong = Long.parseLong(startImei.substring(0, startImei.length() - 1));
			endImeiReverseLong = Long.parseLong(endImei.substring(0, endImei.length() - 1));
			for(int i = 0, imeiSize = modelImeiList.size(); i < imeiSize; i++){
				modelImei = modelImeiList.get(i);
//				下一条数据截取modelImei最后一位加1并补充0，上一条数据截取modelImei最后一位减1并补充9
				modelImeiReverseLong = Long.parseLong(modelImei.substring(0, modelImei.length() - 1));
				if(modelImeiReverseLong < startImeiReverseLong){
					if(i == modelImeiList.size() - 1){
						result.add(imeiSelectModel);
						break;
					}
//					起始号段大于当前样机
    				continue;
//    				起始号段和结束号段都一样，并且大小是1
    			} else if(startImei.equals(modelImei) && size == 1){
    				result.add(imeiSelectModel);
    				break;
    			} else if(startImei.equals(modelImei) && size > 1){
    				tempImeiModel = new ExcelImeiSelectModel();
    				tempImeiModel.setStartImei(modelImei);
    				tempImeiModel.setEndImei(modelImei);
    				tempImeiModel.setModelImei(modelImei);
    				tempImeiModel.setSize(1);
//    				添加样机的上一条数据
    				result.add(tempImeiModel);
    				tempImeiModel = new ExcelImeiSelectModel();
    				startImei = String.valueOf(modelImeiReverseLong + 1) + "0";
    				tempImeiModel.setEndImei(imeiSelectModel.getEndImei());
    				tempImeiModel.setStartImei(startImei);
    				tempImeiModel.setSize(size - 1);
    				tempRecicle = new ArrayList<>();
    				tempRecicle.add(tempImeiModel);
    				imeiDevision(tempRecicle, modelImeiList);
    				break;
    			} else if(modelImeiReverseLong > startImeiReverseLong && modelImeiReverseLong < endImeiReverseLong){
//    				添加样机上一条数据
    				tempImeiModel = new ExcelImeiSelectModel();
    				endImei = String.valueOf(modelImeiReverseLong - 1) + "9";
    				devisionSize = (int) (modelImeiReverseLong - startImeiReverseLong);
    				tempImeiModel.setStartImei(imeiSelectModel.getStartImei());
    				tempImeiModel.setEndImei(endImei);
    				tempImeiModel.setSize(devisionSize);
    				result.add(tempImeiModel);
//    				添加样机这条数据
    				tempImeiModel = new ExcelImeiSelectModel();
    				tempImeiModel.setStartImei(modelImei);
    				tempImeiModel.setEndImei(modelImei);
    				tempImeiModel.setModelImei(modelImei);
    				tempImeiModel.setSize(1);
    				result.add(tempImeiModel);
//    				添加样机下一条数据
    				tempImeiModel = new ExcelImeiSelectModel();
    				startImei = String.valueOf(modelImeiReverseLong + 1) + "0";
    				tempImeiModel.setEndImei(imeiSelectModel.getEndImei());
    				tempImeiModel.setStartImei(startImei);
    				tempImeiModel.setSize(size - devisionSize - 1);
    				tempRecicle = new ArrayList<>();
    				tempRecicle.add(tempImeiModel);
    				imeiDevision(tempRecicle, modelImeiList);
    				break;
//    				起始号段和结束号段不一样，样机只和结束号段相等
    			}else if(endImei.equals(modelImei)){
    				endImei = String.valueOf(modelImeiReverseLong - 1) + "9";
    				imeiSelectModel.setEndImei(endImei);
    				imeiSelectModel.setSize(size - 1);
//    				添加样机的下一条数据
    				result.add(imeiSelectModel);
    				tempImeiModel = new ExcelImeiSelectModel();
    				tempImeiModel.setStartImei(modelImei);
    				tempImeiModel.setSize(1);
    				tempImeiModel.setEndImei(modelImei);
    				tempImeiModel.setModelImei(modelImei);
    				result.add(tempImeiModel);
    				break;
    			}else if(modelImeiReverseLong > endImeiReverseLong){
    				result.add(imeiSelectModel);
    				break;
    			}
    		}
    	}
    }
}