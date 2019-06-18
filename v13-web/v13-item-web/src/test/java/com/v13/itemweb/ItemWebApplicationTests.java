package com.v13.itemweb;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemWebApplicationTests {

    @Autowired
    private Configuration configuration;

    @Test
    public void createHTMLTest() throws IOException, TemplateException {
        //1.获取模板对象
        Template template = configuration.getTemplate("hello.ftl");
        //2.设置模板数据
        Map<String,Object> data = new HashMap<>();
        data.put("username","zs");
        //3.模板+数据，最终生成静态页面
        Writer out = new FileWriter("E:\\ideaWorkspace\\v13\\v13-web\\v13-item-web\\src\\main\\resources\\static\\hello.html");
        template.process(data,out);
        //
        System.out.println("生成静态页面成功！");
    }

    @Test
    public void contextLoads() {
    }

}
