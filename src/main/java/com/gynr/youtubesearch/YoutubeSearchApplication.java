package com.gynr.youtubesearch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.IOException;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTubeRequestInitializer;

@SpringBootApplication
@EnableScheduling
@EnableAsync
public class YoutubeSearchApplication {

	public static void main(String[] args) {
		SpringApplication.run(YoutubeSearchApplication.class, args);
	}

	@Autowired
	private Environment env;

	@Bean
	public YouTube webClient() {
		return new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), new HttpRequestInitializer() {

			public void initialize(HttpRequest request) throws IOException {
			}
		}).setApplicationName("YoutubeVideoInfo")
				.setYouTubeRequestInitializer(new YouTubeRequestInitializer(env.getProperty("youtube.apikey"))).build();
	}

}
