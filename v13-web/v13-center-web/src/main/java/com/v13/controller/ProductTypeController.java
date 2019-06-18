package com.v13.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.v13.api.IProductTypeService;
import com.v13.entity.TProductType;
import com.v13.common.pojo.ResultBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author zsp
 * @Date 2019/6/13
 */
@Controller
@RequestMapping("productType")
public class ProductTypeController {

    @Reference
    private IProductTypeService productTypeService;

    @PostMapping("typeList")
    @ResponseBody
    public ResultBean<List<TProductType>> typeList() {
        List<TProductType> tList = productTypeService.list();
        if (tList != null) {
            return new ResultBean("200", tList);
        }
        return new ResultBean("404", "404");
    }
}
