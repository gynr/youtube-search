package com.gynr.youtubesearch.controller;

import java.util.ArrayList;
import java.util.List;

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

        @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
        public Mono getVideoBySearchQuery(
                        @RequestParam(name = "page", defaultValue = "0", required = false) final Integer page,
                        @RequestParam(name = "size", defaultValue = "10", required = false) final Integer size,
                        @RequestParam(name = "query", required = true) final List<String> query) {

                return Mono.just(new ArrayList<>());

        }

}