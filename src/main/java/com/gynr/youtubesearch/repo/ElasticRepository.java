package com.gynr.youtubesearch.repo;

import com.gynr.youtubesearch.domain.VideoDetail;

import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElasticRepository extends ReactiveElasticsearchRepository<VideoDetail, String> {

}