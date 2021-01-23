//package ftpUpload;
//
//import lombok.extern.slf4j.Slf4j;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@Slf4j
//public class FtpTest {
//
//
//    @Autowired
//    private FTPBean ftpBean;
//
//    @Test
//    public void test() throws FileNotFoundException {
//
//        FtpUtil.downloadFile(ftpBean.getFtpHost(), ftpBean.getFtpUserName(), ftpBean.getFtpPassword(), ftpBean.getFtpPort(),
//                ftpBean.getFtpPath(), ftpBean.getDownloadFile(), ftpBean.getDownloadFileName());
//
//        String filePath=ftpBean.getUploadFile();
//        String fileName= ftpBean.getUploadFileName();
//
//        log.info(filePath+File.separatorChar+fileName);
//
//        FileInputStream input = new FileInputStream(new File(filePath+File.separatorChar+fileName));
//        FtpUtil.uploadFile(ftpBean.getFtpHost(), ftpBean.getFtpUserName(), ftpBean.getFtpPassword(), ftpBean.getFtpPort(), ftpBean.getFtpPath(), fileName, input);
//
//    }
//}