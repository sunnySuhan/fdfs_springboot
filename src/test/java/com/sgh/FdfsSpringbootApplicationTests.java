package com.sgh;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadByteArray;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.github.tobato.fastdfs.service.FastFileStorageClient;

import java.io.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FdfsSpringbootApplicationTests {

    @Autowired
    private FastFileStorageClient storageClient;

    // 测试上传
    @Test
    public void testFileUpload() throws FileNotFoundException {
        File file = new File("D:\\2.jpg");
        FileInputStream inputStream = new FileInputStream(file);
        StorePath storePath = storageClient.uploadFile(inputStream,
                file.length(), "jpg", null);
        System.err.println(storePath.getGroup() + " | " +
                storePath.getPath());
    }

    // 测试下载
    @Test
    public void testDownloadFile() throws IOException {
        byte[] file = storageClient.downloadFile("group1", "M00/00/00/wKhfClzQ8iSAeWpgAArv1KfunAg648.jpg", new DownloadByteArray());
        FileOutputStream fileOutputStream = new FileOutputStream("D:\\d.jpg");
        fileOutputStream.write(file);
        fileOutputStream.close();

    }

    @Test
    public void contextLoads() {

    }

}
