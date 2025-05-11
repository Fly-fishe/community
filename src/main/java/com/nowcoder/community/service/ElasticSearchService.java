package com.nowcoder.community.service;

import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import com.nowcoder.community.dao.elasticsearch.DiscussPostRepository;
import com.nowcoder.community.entity.DiscussPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

@Service
public class ElasticSearchService {

    @Autowired
    private DiscussPostRepository discussPostRepository;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    public void saveDiscussPost(DiscussPost post){
        discussPostRepository.save(post);
    }

    public void deleteDiscussPost(int id){
        discussPostRepository.deleteById(id);
    }

    public Page<DiscussPost> searchDiscussPost(String keyword,int current,int limit) {

        NativeQuery searchQuery = NativeQuery.builder()
                .withQuery(QueryBuilders.multiMatch(m -> m
                        .query(keyword)
                        .fields("title", "content")
                ))
                .withSort(Sort.by(Sort.Direction.DESC, "type", "score", "createTime"))
                .withPageable(PageRequest.of(current, limit))
                .build();
        SearchHits<DiscussPost> searchHits = elasticsearchOperations.search(searchQuery, DiscussPost.class);

        //转换为Page对象
        return new PageImpl<>(
                searchHits.getSearchHits().stream()
                        .map(SearchHit::getContent)
                        .toList(),
                searchQuery.getPageable(),
                searchHits.getTotalHits()

        );



    }

}
