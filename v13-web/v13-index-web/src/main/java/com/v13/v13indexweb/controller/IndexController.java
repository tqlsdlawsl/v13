package com.v13.v13indexweb.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.v13.api.IProductTypeService;
import com.v13.entity.TProductType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author zsp
 * @Date 2019/6/16
 */
@Controller
@RequestMapping("index")
public class IndexController {

    @Reference
    private IProductTypeService productTypeService;

    @RequestMapping("home")
    public String index(Model model){
        List<TProductType> list = productTypeService.list();
        model.addAttribute("list",list);
        return "home";
    }
}
