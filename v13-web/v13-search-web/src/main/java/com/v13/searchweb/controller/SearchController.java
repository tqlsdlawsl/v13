package com.v13.searchweb.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.v13.api.ISearchService;
import com.v13.common.pojo.ResultBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zsp
 * @Date 2019/6/17
 */
@Controller
@RequestMapping("search")
public class SearchController {

    @Reference
    private ISearchService searchService;

    @RequestMapping("queryByKeywords/{keywords}/{currentPage}")
    public String queryByKeywords(@PathVariable("keywords") String keywords ,@PathVariable("currentPage") Integer currentPage, Model model){
        ResultBean resultBean = searchService.queryByKeywords(keywords,currentPage);
        model.addAttribute("result",resultBean);
        model.addAttribute("keywords",keywords);
        return "list";
    }
}
