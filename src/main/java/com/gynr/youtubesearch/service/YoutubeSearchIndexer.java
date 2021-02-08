package com.gynr.youtubesearch.service;

import java.util.List;

import com.gynr.youtubesearch.domain.VideoDetail;

import reactor.core.publisher.Mono;

public interface YoutubeSearchIndexer {

    void index(VideoDetail videoDetail);

    public Mono<List<VideoDetail>> getVideoDetailsBySearchQuery(String query, Integer page, Integer size);

    public Mono<List<VideoDetail>> getLatestVideoDetails(String sortByPublishedTime, Integer page, Integer size);
}
