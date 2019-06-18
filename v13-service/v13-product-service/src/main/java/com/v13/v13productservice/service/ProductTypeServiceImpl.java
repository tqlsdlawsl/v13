package com.v13.v13productservice.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.v13.api.IProductTypeService;
import com.v13.common.base.BaseServiceImpl;
import com.v13.common.base.IBaseDao;
import com.v13.entity.TProductType;
import com.v13.mapper.TProductTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author zsp
 * @Date 2019/6/12
 */
@Service
public class ProductTypeServiceImpl extends BaseServiceImpl<TProductType> implements IProductTypeService {

    @Autowired
    private TProductTypeMapper tProductTypeMapper;

    @Override
    public IBaseDao<TProductType> getBaseDao() {
        return tProductTypeMapper;
    }
}
