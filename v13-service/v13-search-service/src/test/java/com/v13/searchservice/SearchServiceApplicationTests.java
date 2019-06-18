package com.v13.searchservice;

import com.v13.api.ISearchService;
//import com.v13.entity.TProduct;
import com.v13.common.pojo.ResultBean;
import com.v13.entity.TProduct;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SearchServiceApplicationTests {

    @Autowired
    private SolrClient client;

    @Autowired
    private ISearchService searchService;

    @Test
    public void synAllDataTest(){
        ResultBean resultBean = searchService.synAllData();
        System.out.println(resultBean.getStatusCode());
    }

    @Test
    public void synDataById(){
        ResultBean resultBean = searchService.synDataById(13L);
        System.out.println(resultBean.getStatusCode());
    }

    @Test
    public void queryByKeywordsTest(){
        ResultBean resultBean = searchService.queryByKeywords("苹果",0);
        System.out.println(resultBean.getStatusCode()+","+resultBean.getData());
    }

    @Test
    public void addAndUpdateTest() throws IOException, SolrServerException {
        SolrInputDocument document = new SolrInputDocument();
        document.setField("id","1");
        document.setField("product_name","asa");
        document.setField("product_price",222);
        document.setField("product_sale_point","456");
        document.setField("product_images","123");

        client.add(document);
        client.commit();
    }

    @Test
    public void queryTest() throws IOException, SolrServerException {
        SolrQuery query = new SolrQuery();
        query.setQuery("product_keywords:aaa");
        QueryResponse response = client.query(query);
        SolrDocumentList results = response.getResults();
        for (SolrDocument result : results) {
            System.out.println(result.get("product_name"));
        }
        client.commit();
    }

    @Test
    public void delById() throws IOException, SolrServerException {
        client.deleteById("1");
        client.commit();
    }

    @Test
    public void delAllTest() throws IOException, SolrServerException {
        //都会分词之后，再匹配
        client.deleteByQuery("*:*");
        client.commit();
    }

}
