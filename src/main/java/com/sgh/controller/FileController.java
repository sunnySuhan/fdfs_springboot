package com.sgh.controller;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

@Controller
@RequestMapping("file")
public class FileController {

    @Autowired
    private FastFileStorageClient storageClient;

    @RequestMapping("upload")
    public void upload(MultipartFile file) {

        //File file1 = null;
        InputStream inputStream = null;
        String ext = null;
        try {
            //file1 = new File("D:\\2.jpg");
            inputStream = file.getInputStream();
            ext = FilenameUtils.getExtension(file.getOriginalFilename());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        StorePath storePath = storageClient.uploadFile(inputStream,
                file.getSize(), ext, null);
        System.err.println(storePath.getGroup() + " | " +
                storePath.getPath());
    }

    //  需要手动在地址栏输入group和保存的文件名  如  M00/00/0A/wKhfDVzRROCAXdwCABfz6yAOFTM137.mp3
    @RequestMapping("download")
    public void downloadFile(String group, String remoteFileName, HttpServletResponse response) {
        byte[] file = storageClient.downloadFile(group, remoteFileName, new DownloadByteArray());

        try {
            response.setContentType("text/html;charset=utf-8");
            response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(remoteFileName, "UTF-8"));
            OutputStream fileOutputStream = response.getOutputStream();
            fileOutputStream.write(file);
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
