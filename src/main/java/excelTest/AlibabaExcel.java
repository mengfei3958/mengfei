package excelTest;

import java.io.BufferedInputStream;
import java.io.File;
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
import com.example.demo.excelEntity.ExcelPropertyIndexModel;

public class AlibabaExcel {

	private List<ExcelPropertyIndexModel> datas = new ArrayList<>();

	@Test
	public void excelReadOfAlibaba() throws Exception {
		InputStream inputStream = new BufferedInputStream(new FileInputStream("C:\\Users\\mengfei\\Desktop\\student.xls"));
		@SuppressWarnings("rawtypes")
		AnalysisEventListener listener = new AnalysisEventListener(){

			@Override
			public void invoke(Object object, AnalysisContext context) {
				// TODO Auto-generated method stub
//				一次获取一条数据，使用全局变量data接收对象信息
				datas.add((ExcelPropertyIndexModel) object);
			}

			@Override
			public void doAfterAllAnalysed(AnalysisContext paramAnalysisContext) {
				// TODO Auto-generated method stub

			}};
		ExcelReader excelReader = new ExcelReader(inputStream, null, listener);
//		从第一个sheet页的第二行读取数据
		excelReader.read(new Sheet(1, 1, ExcelPropertyIndexModel.class));
		excelWriteOfAlibaba();
	}


//    @Test
    public void excelWriteOfAlibaba() throws FileNotFoundException {
        OutputStream out = new FileOutputStream("C:\\Users\\mengfei\\Desktop\\writeStudent.xlsx");
        try {
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX);
            //写第一个sheet, sheet1  数据全是List<ExcelPropertyIndexModel> 模型映射关系
            Sheet sheet = new Sheet(1, 0,ExcelPropertyIndexModel.class);
            writer.write(datas, sheet);
//            writer.write(getData(), sheet);
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


//    private List<ExcelPropertyIndexModel> getData(){
//    	List<ExcelPropertyIndexModel> list = new ArrayList<>();
//    	ExcelPropertyIndexModel exIndexModel = new ExcelPropertyIndexModel();
//    	exIndexModel.setName("倩倩");
//    	exIndexModel.setAge("26");
//    	exIndexModel.setEmail("963644259");
//    	exIndexModel.setAddress("陕西咸阳");
//    	exIndexModel.setSex("女");
//    	list.add(exIndexModel);
//    	return list;
//    }
  
}
