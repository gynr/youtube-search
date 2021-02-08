package com.gynr.youtubesearch.service.impl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public void fetch(String query) throws IOException {

        YouTube.Search.List search;

        search = youtube.search().list("id,snippet");

        search.setQ(query);
        search.setType("video");
        search.setMaxResults(50L);

        String publishAfter = LocalDateTime.now().minus(10, ChronoUnit.SECONDS).toString();
        search.setPublishedAfter(new DateTime(publishAfter));

        SearchListResponse searchResponse = search.execute();

        List<SearchResult> searchResultList = searchResponse.getItems();

        if (searchResultList != null) {
            searchResultList.stream().map(m -> createVideoDetail(m)).forEach(m -> log.info(m.toString()));
        }
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
        // videoDetail.setPublishedAt(snippet.getPublishedAt());

        ThumbnailDetails thumnailDetails = snippet.getThumbnails();
        Map<String, String> thumnails = new HashMap<>();
        thumnails.put("default", thumnailDetails.getDefault().getUrl());
        thumnails.put("medium", thumnailDetails.getMedium().getUrl());
        thumnails.put("high", thumnailDetails.getHigh().getUrl());
        videoDetail.setThumbnails(thumnails);

        // videoDetail.setViewCount(snippet.getViewCount());
        // videoDetail.setLikeCount(snippet.getLikeCount());
        // videoDetail.setDislikeCount(snippet.getDislikeCount());
        // videoDetail.setFavoriteCount(snippet.getFavoriteCount());
        // videoDetail.setCommentCount(snippet.getCommentCount());

        return videoDetail;
    }

}
