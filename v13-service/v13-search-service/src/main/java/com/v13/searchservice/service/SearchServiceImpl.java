package com.v13.searchservice.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.v13.api.ISearchService;
import com.v13.common.pojo.PageResultBean;
import com.v13.entity.TProduct;
import com.v13.mapper.TProductMapper;
import com.v13.common.pojo.ResultBean;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zsp
 * @Date 2019/6/17
 */

@Service
public class SearchServiceImpl implements ISearchService {

    @Autowired
    private TProductMapper productMapper;

    @Autowired
    private SolrClient solrClient;

    @Override
    public ResultBean synAllData() {
        //1.获取数据库的数据
        List<TProduct> list = productMapper.list();
        //2.将数据导入到索引库中
        for (TProduct product : list) {
            //3.product -> solrInputDocument
            SolrInputDocument document = new SolrInputDocument();
            document.setField("id",product.getId());
            document.setField("product_name",product.getName());
            document.setField("product_price",product.getPrice());
            document.setField("product_images",product.getImages());
            document.setField("product_sale_point",product.getSalePoint());
            //
            try {
                solrClient.add(document);
            } catch (SolrServerException | IOException e) {
                e.printStackTrace();
                return new ResultBean("404","数据添加到索引库失败！");
            }
        }
        try {
            solrClient.commit();
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
            return new ResultBean("404","数据提交到索引库失败！");
        }
        return new ResultBean("200","数据同步到索引库成功！");
    }

    @Override
    public ResultBean synDataById(Long id) {
        TProduct product = productMapper.selectByPrimaryKey(id);
        SolrInputDocument document = new SolrInputDocument();
        document.setField("id",product.getId());
        document.setField("product_name",product.getName());
        document.setField("product_price",product.getPrice());
        document.setField("product_images",product.getImages());
        document.setField("product_sale_point",product.getSalePoint());
        try {
            solrClient.add(document);
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
            return new ResultBean("404","数据添加到索引库失败！");
        }
        try {
            solrClient.commit();
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
            return new ResultBean("404","数据提交到索引库失败！");
        }
        return new ResultBean("200","数据同步到索引库成功！");
    }

    @Override
    public ResultBean queryByKeywords(String keywords,Integer currentPage) {
        SolrQuery query = new SolrQuery();
        if (StringUtils.isAnyEmpty(keywords)){
            query.setQuery("*:*");
        }else {
            query.setQuery("product_keywords:"+keywords);
        }

        query.setHighlight(true);
        query.addHighlightField("product_name");
        query.setHighlightSimplePre("<font color = 'red'>");
        query.setHighlightSimplePost("</font>");
        PageResultBean<TProduct> page = new PageResultBean<>();
        page.setPageNum(currentPage);
        page.setPageSize(5);
//        query.setStart((page.getPageNum()-1)*page.getPageSize());
//        query.setRows(page.getPageSize());
        try {
            QueryResponse allresponse = solrClient.query(query);
            SolrDocumentList allresults = allresponse.getResults();
            page.setPages(allresults.size()%page.getPageSize() > 0 ? allresults.size()/page.getPageSize() + 1 : allresults.size()/page.getPageSize());
            page.setNavigatePages(3);

            query.setStart((page.getPageNum()-1)*page.getPageSize());
            query.setRows(page.getPageSize());
            QueryResponse response = solrClient.query(query);
            SolrDocumentList results = response.getResults();
            List<TProduct> list = new ArrayList<>(results.size());
            Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
            for (SolrDocument document : results) {
                TProduct product = new TProduct();
                product.setId(Long.parseLong(document.getFieldValue("id").toString()));
//                product.setName(document.getFieldValue("product_name").toString());
                product.setImages(document.getFieldValue("product_images").toString());
                product.setPrice(Long.parseLong(document.getFieldValue("product_price").toString()));
                product.setSalePoint(document.getFieldValue("product_sale_point").toString());
                //
                Map<String, List<String>> idHigh = highlighting.get(document.getFieldValue("id"));
                List<String> productNameHigh = idHigh.get("product_name");
                if (productNameHigh==null||productNameHigh.isEmpty()){
                    product.setName(document.getFieldValue("product_name").toString());
                }else {
                    product.setName(productNameHigh.get(0));
                }
                list.add(product);
                page.setList(list);
            }
            return new ResultBean("200",page);
        }catch (SolrServerException | IOException e) {
            e.printStackTrace();
            return new ResultBean("404","数据查询索引库失败！");
        }
    }
}
