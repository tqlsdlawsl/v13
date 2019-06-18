package com.v13.controller;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.v13.common.pojo.ResultBean;
import com.v13.common.pojo.WangeditorResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author zsp
 * @Date 2019/6/13
 */
@Controller
@RequestMapping("file")
public class FileController {

    @Autowired
    private FastFileStorageClient client;

    @Value("${image.server}")
    private String imageServer;

    @RequestMapping("upload")
    @ResponseBody
    public ResultBean upload(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String lastName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        try {
            StorePath storePath = client.uploadFile(file.getInputStream(), file.getSize(), lastName, null);
            String path = new StringBuilder(imageServer).append(storePath.getFullPath()).toString();
            return new ResultBean("200", path);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResultBean("404", "404");
        }
    }

    @PostMapping("multiUpload")
    @ResponseBody
    public WangeditorResultBean multiUpload(MultipartFile[] files) {
        String[] data = new String[files.length];
        //依次上传文件
        for (int i = 0; i < files.length; i++) {
            String originalFilename = files[i].getOriginalFilename();//**.**
            String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            try {
                StorePath storePath =
                        client.uploadFile(files[i].getInputStream(), files[i].getSize(), extName, null);
                String path = new StringBuilder(imageServer).append(storePath.getFullPath()).toString();
                //将数据保存到数组中
                data[i] = path;
            } catch (IOException e) {
                e.printStackTrace();
                return new WangeditorResultBean("1", null);
            }
        }
        return new WangeditorResultBean("0", data);
    }
}
