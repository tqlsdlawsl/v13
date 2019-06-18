package com.v13.api;

import com.v13.common.pojo.ResultBean;

/**
 * @author zsp
 * @Date 2019/6/17
 */
public interface ISearchService {
    public ResultBean synAllData();

    public ResultBean synDataById(Long id);

    public ResultBean queryByKeywords(String keywords,Integer currentPage);
}
