package com.gynr.youtubesearch.service.impl;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.api.client.util.DateTime;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.SearchResultSnippet;
import com.google.api.services.youtube.model.ThumbnailDetails;
import com.gynr.youtubesearch.domain.VideoDetail;
import com.gynr.youtubesearch.service.HttpService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class HttpServiceImpl implements HttpService {

    @Autowired
    YouTube youtube;

    public List<VideoDetail> fetchVideoDetails(String query) throws IOException {

        YouTube.Search.List search;

        search = youtube.search().list("id,snippet");

        search.setQ(query);
        search.setType("video");
        search.setMaxResults(50L);
        search.setOrder("date");

        String publishAfter = LocalDateTime.now().minus(1, ChronoUnit.HOURS).toString();
        search.setPublishedAfter(new DateTime(publishAfter));

        SearchListResponse searchResponse = search.execute();

        List<SearchResult> searchResultList = searchResponse.getItems();

        List<VideoDetail> videoDetails = new ArrayList<>();

        if (searchResultList != null) {
            videoDetails = searchResultList.stream().map(m -> createVideoDetail(m)).collect(Collectors.toList());
        }

        return videoDetails;
    }

    private VideoDetail createVideoDetail(SearchResult m) {

        VideoDetail videoDetail = new VideoDetail();
        SearchResultSnippet snippet = m.getSnippet();
        ResourceId resourceId = m.getId();

        videoDetail.setId(resourceId.getVideoId());
        videoDetail.setTitle(snippet.getTitle());
        videoDetail.setDescription(snippet.getDescription());
        videoDetail.setChannelId(snippet.getChannelId());
        videoDetail.setChannelTitle(snippet.getChannelTitle());

        Date publishedAt = Date.from(Instant.ofEpochMilli(snippet.getPublishedAt().getValue()));
        videoDetail.setPublishedAt(publishedAt);

        ThumbnailDetails thumnailDetails = snippet.getThumbnails();
        Map<String, String> thumnails = new HashMap<>();
        thumnails.put("default", thumnailDetails.getDefault().getUrl());
        thumnails.put("medium", thumnailDetails.getMedium().getUrl());
        thumnails.put("high", thumnailDetails.getHigh().getUrl());
        videoDetail.setThumbnails(thumnails);

        return videoDetail;
    }

}
