package com.gynr.youtubesearch.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.gynr.youtubesearch.constant.Fields;
import com.gynr.youtubesearch.domain.VideoDetail;
import com.gynr.youtubesearch.repo.ElasticRepository;
import com.gynr.youtubesearch.service.YoutubeSearchIndexer;

import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ReactiveElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class YoutubeSearchIndexerImpl implements YoutubeSearchIndexer {

    @Autowired
    ElasticRepository elasticRepository;

    @Autowired
    ReactiveElasticsearchTemplate elasticsearchTemplate;

    @Override
    public void index(VideoDetail videoDetail) {
        elasticRepository.save(videoDetail).subscribe();

    }

    @Override
    public Mono<List<VideoDetail>> getVideoDetailsBySearchQuery(String query, Integer page, Integer size) {

        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

        boolQuery.must(QueryBuilders.queryStringQuery(query).field(Fields.TITLE).field(Fields.DESCRIPTION)
                .fuzziness(Fuzziness.AUTO));

        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(boolQuery)
                .withPageable(PageRequest.of(page, size)).build();

        return elasticsearchTemplate.search(searchQuery, VideoDetail.class).map(m -> m.getContent()).collectList();
    }

    @Override
    public Mono<List<VideoDetail>> getLatestVideoDetails(String sortByPublishedTime, Integer page, Integer size) {

        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(QueryBuilders.matchAllQuery())
                .withPageable(PageRequest.of(page, size))
                .withSort(SortBuilders.fieldSort(Fields.PUBLISHED_AT).order(SortOrder.DESC)).build();

        return elasticsearchTemplate.search(searchQuery, VideoDetail.class).map(m -> m.getContent()).collectList();
    }

}
