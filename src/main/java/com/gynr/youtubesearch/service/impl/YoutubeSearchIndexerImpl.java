package com.gynr.youtubesearch.service.impl;

import java.io.IOException;

import com.gynr.youtubesearch.domain.VideoDetail;
import com.gynr.youtubesearch.repo.ElasticRepository;
import com.gynr.youtubesearch.service.HttpService;
import com.gynr.youtubesearch.service.YoutubeSearchIndexer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ReactiveElasticsearchTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@Service
@Slf4j
public class YoutubeSearchIndexerImpl implements YoutubeSearchIndexer {

    @Autowired
    ElasticRepository elasticRepository;

    @Autowired
    ReactiveElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    HttpService httpService;

    @Override
    public void index(VideoDetail videoDetail) {
        elasticRepository.save(videoDetail).subscribe();

    }

    @Override
    @Scheduled(fixedRateString = "${youtube.indexer.interval:10000}")
    public void scheduler() throws IOException {

        Scheduler scheduler = Schedulers.newBoundedElastic(5, 20, "YouTubeSearchTermPool");
        log.info("Trying to retrive results...");
        Flux.fromIterable(httpService.fetchVideoDetails("football")).publishOn(scheduler)
                .subscribe(videoDetail -> index(videoDetail));
        log.info("Done get results.");

    }

}
