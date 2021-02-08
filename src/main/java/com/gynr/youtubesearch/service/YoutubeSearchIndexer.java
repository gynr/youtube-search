package com.gynr.youtubesearch.service;

import com.gynr.youtubesearch.domain.VideoDetail;

public interface YoutubeSearchIndexer {

    void index(VideoDetail videoDetail);

}
