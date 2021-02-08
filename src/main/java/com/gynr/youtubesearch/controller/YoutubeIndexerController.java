package com.gynr.youtubesearch.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.gynr.youtubesearch.service.HttpService;
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
public class YoutubeIndexerController {

        @Autowired
        HttpService httpService;

        @Autowired
        YoutubeSearchIndexer youtubeSearchIndexer;

        @GetMapping(value = "/index/latest", produces = MediaType.APPLICATION_JSON_VALUE)
        public Mono getVideoBySearchQuery(@RequestParam(name = "query", required = true) final String query) {
                log.info("Trying to retrive results...");
                try {
                        httpService.fetchVideoDetails(query).forEach(m -> youtubeSearchIndexer.index(m));
                } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }

                log.info("Done get results.");
                return Mono.just(new ArrayList<>());
        }

}