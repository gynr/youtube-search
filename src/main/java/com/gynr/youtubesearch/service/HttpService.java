package com.gynr.youtubesearch.service;

import java.io.IOException;
import java.util.List;

import com.gynr.youtubesearch.domain.VideoDetail;

public interface HttpService {

    public List<VideoDetail> fetchVideoDetails(String query) throws IOException;
}
