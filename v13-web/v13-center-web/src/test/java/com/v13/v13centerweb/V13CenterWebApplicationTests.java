package com.v13.v13centerweb;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class V13CenterWebApplicationTests {

    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    @Test
    public void contextLoads() {
        File file = new File("E:\\ideaWorkspace\\v13\\v13-web\\v13-center-web\\1.jpg");
        File file2 = new File("C:\\Users\\Administrator\\Desktop\\2.jpg");
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            StorePath storePath = fastFileStorageClient.uploadFile(fileInputStream, file.length(), "jpg", null);
            System.out.println(storePath.getFullPath());
            System.out.println(storePath.getGroup());
            System.out.println(storePath.getPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void delTest() {
        fastFileStorageClient.deleteFile("group1/M00/00/00/rBECh10CM4SAN-FbAAAI835_ABw767.jpg");
        System.out.println("delete success!");
    }

}
