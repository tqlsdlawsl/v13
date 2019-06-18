package com.v13.v13productservice;

import com.v13.api.IProductService;
import com.v13.api.IProductTypeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class V13ProductServiceApplicationTests {

    @Autowired
    private IProductService productService;

    @Autowired
    private IProductTypeService productTypeService;

    @Test
    public void contextLoads() {
//        TProduct tProduct = productService.selectByPrimaryKey(1L);
//        System.out.println(tProduct.getName());
//        PageInfo<TProduct> page = productService.page(1, 2);
//        for (TProduct tProduct : page.getList()) {
//            System.out.println(tProduct.getName());
//        }
//        TProduct tProduct = new TProduct();
//        tProduct.setName("梨");
//        tProduct.setImages("无");
//        tProduct.setPrice(10L);
//        tProduct.setSalePoint("梨");
//        tProduct.setSalePrice(9L);
//        tProduct.setTypeId(6L);
//        tProduct.setTypeName("水果");
//        TProductVO vo = new TProductVO();
//        vo.setProduct(tProduct);
//        vo.setProductDesc("很好吃");
//        System.out.println(productService.save(vo));
//        System.out.println(productService.deleteByPrimaryKey(1L));
//        List<Long> list = new ArrayList<>();
//        list.add(1L);
//        list.add(2L);
//        productService.batchDel(list);
//        System.out.println(productTypeService.list().size());
//        System.out.println(productService.selectByPrimaryKey(1L));


    }


}
