package com.v13.v13productservice.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.v13.api.IProductService;
import com.v13.common.base.BaseServiceImpl;
import com.v13.common.base.IBaseDao;
import com.v13.entity.TProduct;
import com.v13.entity.TProductDesc;
import com.v13.mapper.TProductDescMapper;
import com.v13.mapper.TProductMapper;
import com.v13.common.pojo.TProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zsp
 * @Date 2019/6/11
 */
@Service
public class ProductServiceImpl extends BaseServiceImpl<TProduct> implements IProductService {

    @Autowired
    private TProductMapper tProductMapper;

    @Autowired
    private TProductDescMapper tProductDescMapper;

    public IBaseDao<TProduct> getBaseDao() {
        return tProductMapper;
    }

    @Override
    public PageInfo<TProduct> page(Integer pageIndex, Integer pageSize) {
        PageHelper.startPage(pageIndex,pageSize);
        List<TProduct> list = list();
        PageInfo<TProduct> pageInfo = new PageInfo<TProduct>(list,2);

        return pageInfo;
    }

    @Override
    @Transactional
    public Long save(TProductVO vo) {
        TProduct tProduct = vo.getProduct();
        tProduct.setFlag(true);
        int i = tProductMapper.insert(tProduct);
        TProductDesc tProductDesc = new TProductDesc();
        tProductDesc.setProductDesc(vo.getProductDesc());
        tProductDesc.setProductId(tProduct.getId());
        tProductDescMapper.insert(tProductDesc);

        return tProduct.getId();
    }

    @Override
    @Transactional
    public Long batchDel(List<Long> ids) {
        Long count = tProductMapper.batchUpdateFlag(ids);
        return count;
    }

    @Override
    @Transactional
    public int deleteByPrimaryKey(Long id){
        TProduct tProduct = new TProduct();
        tProduct.setId(id);
        tProduct.setFlag(false);
        int count = tProductMapper.updateByPrimaryKeySelective(tProduct);
        return count;
    }
}
