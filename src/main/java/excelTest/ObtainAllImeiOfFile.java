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
import com.example.demo.excelEntity.ExcelFileNamesModel;
import com.example.demo.excelEntity.ExcelImeiOfFileNames;

/**
 * 根据原始文件获取imei列表和文件名称，生成imei.xlsx
 * @author 0193000683
 *
 */
public class ObtainAllImeiOfFile {
	
//	提取原始文件中的IMEI以及imei数量
	private List<ExcelImeiOfFileNames> imeiOfFileNames = new ArrayList<>();
	
//	提取原始文件中的文件名称以及大小
	private List<ExcelFileNamesModel> fileNames = new ArrayList<>();
	
//	imei数据源的文件目录路径
	private final String FILE_PATH = "D:\\mengfei\\test\\filenames\\";

//	将所有imei写入Excel表中
	private String IMEIS_FILE_NAME = "D:\\mengfei\\test\\imei\\imei.xlsx";
	
//	将目录下的文件名称写入Excel表中
	private String WRITE_FILE_PATH = "D:\\mengfei\\test\\imei\\writeFileNames.xlsx";

	@Test
	public void getFileNames() throws Exception {
		File file = new File(FILE_PATH);
		File[] files = file.listFiles();
		String filePath;
//		文件名称
		String fileName;
//		文件大小数量
		int number;
		int sum = 0;
		ExcelFileNamesModel excelFileName;
		for (File filename:files) {
			filePath = filename.getPath();
//			先截取文件路径的最后一个_，然后在截取倒数第二个_，以获取数字（根据文件命名方式获取number）
			fileName = filePath.substring(0, filePath.lastIndexOf("_"));
			number = Integer.parseInt(fileName.substring(fileName.lastIndexOf("_") + 1).replace("pcs", ""));
//			输出文件名称以及大小
			System.out.println(fileName + "------------" + (number + ""));
			sum += number;
			excelReadOfFileName(filename.getPath());
			excelFileName = new ExcelFileNamesModel();
//			截取文件名称
			excelFileName.setFileName(filePath.substring(filePath.lastIndexOf("\\") + 1));
			excelFileName.setSize(number);
			fileNames.add(excelFileName);
		}
		System.out.println("总和为----- " + sum);
		excelWriteImeiOfFileName();
		excelWriteFileNames();
	}
	
	
//	读取每个文件中的imei号
	public void excelReadOfFileName(String filePath) throws Exception {
		InputStream inputStream = new BufferedInputStream(new FileInputStream(filePath));
		@SuppressWarnings("rawtypes")
		AnalysisEventListener listener = new AnalysisEventListener(){

			@Override
			public void invoke(Object object, AnalysisContext context) {
				// TODO Auto-generated method stub
//				加载表格数据
				imeiOfFileNames.add((ExcelImeiOfFileNames) object);
			}

			@Override
			public void doAfterAllAnalysed(AnalysisContext paramAnalysisContext) {
				// TODO Auto-generated method stub
			}};
		ExcelReader excelReader = new ExcelReader(inputStream, null, listener);
		excelReader.read(new Sheet(1, 1, ExcelImeiOfFileNames.class));
	}


//	读取完imei号之后，一次性写入Excel表格中
    public void excelWriteImeiOfFileName() throws FileNotFoundException {
        OutputStream out = new FileOutputStream(IMEIS_FILE_NAME);
        try {
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX);
            Sheet sheet = new Sheet(1, 0, ExcelImeiOfFileNames.class);
            writer.write(imeiOfFileNames, sheet);
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
    
//	将文件夹下面的文件名称写入Excel表格中
    public void excelWriteFileNames() throws FileNotFoundException {
        OutputStream out = new FileOutputStream(WRITE_FILE_PATH);
        try {
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX);
            Sheet sheet = new Sheet(1, 0, ExcelFileNamesModel.class);
            writer.write(fileNames, sheet);
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
