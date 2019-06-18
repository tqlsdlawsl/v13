package com.v13.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.v13.api.IProductService;
import com.v13.api.IProductTypeService;
import com.v13.api.ISearchService;
import com.v13.entity.TProduct;
import com.v13.entity.TProductType;
import com.v13.common.pojo.ResultBean;
import com.v13.common.pojo.TProductVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zsp
 * @Date 2019/6/11
 */
@Controller
@RequestMapping("product")
public class ProductController {

    @Reference
    private IProductService productService;

    @Reference
    private IProductTypeService productTypeService;

    @Reference
    private ISearchService searchService;

    @RequestMapping("get/{id}")
    @ResponseBody
    public TProduct getById(@PathVariable("id") Long id) {
        return productService.selectByPrimaryKey(id);
    }

    @RequestMapping("page/{pageIndex}/{pageSize}")
    public String page(@PathVariable("pageIndex") Integer pageIndex,
                       @PathVariable("pageSize") Integer pageSize,
                       Model model) {
        PageInfo<TProduct> page = productService.page(pageIndex, pageSize);
        model.addAttribute("page", page);
        List<TProductType> tList = productTypeService.list();
        model.addAttribute("tList", tList);
        return "product/list";
    }

    @PostMapping("add")
    public String add(TProductVO vo) {
        Long id = productService.save(vo);
        searchService.synDataById(id);
        return "redirect:/product/page/1/5";
    }

    @PostMapping("delById/{id}")
    @ResponseBody
    public ResultBean delById(@PathVariable("id") Long id) {
        int count = productService.deleteByPrimaryKey(id);
        if (count > 0) {
            return new ResultBean("200", "删除成功");
        }
        return new ResultBean("404", "404");
    }

    @PostMapping("batchDel")
    @ResponseBody
    public ResultBean batchDel(@RequestParam("ids") List<Long> ids) {
        Long count = productService.batchDel(ids);
        if (count > 0) {
            return new ResultBean("200", "删除成功");
        }
        return new ResultBean("404", "404");
    }

    @PostMapping("selectOne/{id}")
    @ResponseBody
    public ResultBean<TProduct> selectOne(@PathVariable("id") Long id) {
        TProduct product = productService.selectByPrimaryKey(id);
        if (product != null) {
            return new ResultBean("200", product);
        }
        return new ResultBean("404", 404);
    }

    @PostMapping("update")
    public String update(TProductVO vo) {
        TProduct tProduct = vo.getProduct();
        productService.updateByPrimaryKeySelective(tProduct);
        return "redirect:/product/page/1/5";
    }

}
