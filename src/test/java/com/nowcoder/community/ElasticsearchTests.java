package com.nowcoder.community;

import co.elastic.clients.elasticsearch._types.query_dsl.MultiMatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.util.ObjectBuilder;
import com.nowcoder.community.dao.DiscussPostMapper;
import com.nowcoder.community.dao.elasticsearch.DiscussPostRepository;
import com.nowcoder.community.entity.DiscussPost;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.HighlightQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.core.query.SearchTemplateQuery;
import org.springframework.data.elasticsearch.core.query.highlight.Highlight;
import org.springframework.data.elasticsearch.core.query.highlight.HighlightField;
import org.springframework.data.elasticsearch.core.query.highlight.HighlightParameters;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.swing.text.Highlighter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class ElasticsearchTests {

    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Autowired
    private DiscussPostRepository discussRepository;

    @Autowired
    private ElasticsearchTemplate elasticTemplate;

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    @Test
    public void testInsert(){
        discussRepository.save(discussPostMapper.selectDiscussPostById(241));
        discussRepository.save(discussPostMapper.selectDiscussPostById(242));
        discussRepository.save(discussPostMapper.selectDiscussPostById(243));

    }

    @Test
    public void testInsertList(){
        discussRepository.saveAll(discussPostMapper.selectDiscussPosts(101,0,100));
        discussRepository.saveAll(discussPostMapper.selectDiscussPosts(102,0,100));
        discussRepository.saveAll(discussPostMapper.selectDiscussPosts(103,0,100));
        discussRepository.saveAll(discussPostMapper.selectDiscussPosts(111,0,100));
        discussRepository.saveAll(discussPostMapper.selectDiscussPosts(112,0,100));
        discussRepository.saveAll(discussPostMapper.selectDiscussPosts(131,0,100));
        discussRepository.saveAll(discussPostMapper.selectDiscussPosts(132,0,100));
        discussRepository.saveAll(discussPostMapper.selectDiscussPosts(133,0,100));
        discussRepository.saveAll(discussPostMapper.selectDiscussPosts(134,0,100));

    }

    @Test
    public  void testUpdate(){
        DiscussPost post = discussPostMapper.selectDiscussPostById(231);
        post.setContent("我是新人，使劲灌水.");
        discussRepository.save(post);
    }

    @Test
    public void testDelete(){
        //discussRepository.deleteById(231);
       // discussRepository.deleteAll();
    }


    @Test
    public void testSearchByRepository()  {


        // 构建查询（包含高亮配置）
        NativeQuery searchQuery = NativeQuery.builder()
                .withQuery(QueryBuilders.multiMatch(m -> m
                        .query("互联网寒冬")
                        .fields("title", "content")
                ))
                .withSort(Sort.by(Sort.Direction.DESC, "type", "score", "createTime"))
                .withPageable(PageRequest.of(0, 10))
                .build();

        SearchHits<DiscussPost> searchHits = elasticsearchOperations.search(searchQuery, DiscussPost.class);

        System.out.println("总记录数: " + searchHits.getTotalHits());
        System.out.println("当前页大小: " + searchHits.getSearchHits().size());
        for (SearchHit<DiscussPost> hit : searchHits) {
            System.out.println("得分: " + hit.getScore());
            System.out.println("文档: " + hit.getContent());
            System.out.println("-------------------");
        }
}


}
