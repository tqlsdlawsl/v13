package com.v13.itemweb.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.v13.api.IProductService;
import com.v13.common.pojo.ResultBean;
import com.v13.entity.TProduct;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;


/**
 * @author zsp
 * @Date 2019/6/18
 */
@Controller
@RequestMapping("item")
public class ItemController {

    @Reference
    private IProductService productService;

    @Autowired
    private Configuration configuration;

    @RequestMapping("createHTMLById/{id}")
    @ResponseBody
    public ResultBean createHTMLById(@PathVariable("id") Long id) {
        TProduct product = productService.selectByPrimaryKey(id);
        //1.获取模板对象
        try {
            Template template = configuration.getTemplate("item.ftl");

            //2.设置模板数据
            Map<String, Object> data = new HashMap<>();
            data.put("product", product);
            //3.模板+数据，最终生成静态页面
            String staticPath = ResourceUtils.getURL("classpath:static").getPath();
            String path = new StringBuilder(staticPath).append(File.separator).append(id).append(".html").toString();
            Writer out = new FileWriter(path);
            template.process(data, out);
            return new ResultBean("200", "生成静态页成功");
            //
        } catch (IOException e) {
            e.printStackTrace();
            return new ResultBean("404", "读取模板失败");
        } catch (TemplateException e) {
            e.printStackTrace();
            return new ResultBean("404", "生成静态页失败");
        }
    }
}
