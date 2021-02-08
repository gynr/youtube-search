package com.gynr.youtubesearch.controller;

import java.util.ArrayList;
import java.util.List;

import com.gynr.youtubesearch.domain.VideoDetail;
import com.gynr.youtubesearch.service.YoutubeSearchIndexer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/api/v1/youtube")
@CrossOrigin
public class YoutubeSearchController {

        @Autowired
        YoutubeSearchIndexer youtubeSearchIndexer;

        @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
        public Mono<List<VideoDetail>> getVideoBySearchQuery(
                        @RequestParam(name = "page", defaultValue = "0", required = false) final Integer page,
                        @RequestParam(name = "size", defaultValue = "10", required = false) final Integer size,
                        @RequestParam(name = "query", required = true) final String query) {

                return youtubeSearchIndexer.getVideoDetailsBySearchQuery(query, page, size);

        }

        @GetMapping(value = "/latest", produces = MediaType.APPLICATION_JSON_VALUE)
        public Mono<List<VideoDetail>> getLastestVideos(
                        @RequestParam(name = "page", defaultValue = "0", required = false) final Integer page,
                        @RequestParam(name = "size", defaultValue = "10", required = false) final Integer size,
                        @RequestParam(name = "sortByPublishedTime", defaultValue = "desc", required = false) final String sortByPublishedTime) {

                return youtubeSearchIndexer.getLatestVideoDetails(sortByPublishedTime, page, size);

        }

}