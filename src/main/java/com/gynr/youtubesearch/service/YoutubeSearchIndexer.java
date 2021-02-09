package com.gynr.youtubesearch.service;

import java.io.IOException;
import java.util.List;

import com.gynr.youtubesearch.domain.VideoDetail;

import reactor.core.publisher.Mono;

public interface YoutubeSearchIndexer {

    void index(VideoDetail videoDetail);

    void scheduler() throws IOException;

}
