package com.v13.api;

import com.github.pagehelper.PageInfo;
import com.v13.common.base.IBaseService;
import com.v13.entity.TProduct;
import com.v13.common.pojo.TProductVO;

import java.util.List;

/**
 * @author zsp
 * @Date 2019/6/11
 */
public interface IProductService extends IBaseService<TProduct> {

    public PageInfo<TProduct> page(Integer pageIndex, Integer pageSize);

    public Long save(TProductVO vo);

    public Long batchDel(List<Long> ids);
}
