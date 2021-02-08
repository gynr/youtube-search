package com.gynr.youtubesearch.service.impl;

import com.gynr.youtubesearch.domain.VideoDetail;
import com.gynr.youtubesearch.repo.ElasticRepository;
import com.gynr.youtubesearch.service.YoutubeSearchIndexer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class YoutubeSearchIndexerImpl implements YoutubeSearchIndexer {

    @Autowired
    ElasticRepository elasticRepository;

    @Override
    public void index(VideoDetail videoDetail) {
        elasticRepository.save(videoDetail);

    }

}
