package ftpUpload;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "ftp")
public class FTPBean {

    private String ftpHost;

    private Integer ftpPort;

    private String ftpUserName;

    private String ftpPassword;

    private String ftpPath;

    private String filePath;

    private String fileName;

    private String uploadFile;

    private String uploadFileName;

    private String downloadFile;

    private String downloadFileName;


}
