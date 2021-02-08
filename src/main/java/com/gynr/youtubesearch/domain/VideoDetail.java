package com.gynr.youtubesearch.domain;

import java.util.Date;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFilter;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Document(indexName = "youtube-latest", shards = 1, replicas = 0)
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter(value = "beanFilter")
public class VideoDetail {

    @Id
    String id;

    @Field(type = FieldType.Text, store = true)
    String title;

    @Field(type = FieldType.Text, store = true)
    String description;

    @Field(type = FieldType.Keyword, store = true)
    String channelId;

    @Field(type = FieldType.Text, store = true)
    String channelTitle;

    private Date publishedAt;

    @Field(type = FieldType.Object, store = true)
    private Map<String, String> thumbnails;

    // Integer viewCount;
    // Integer likeCount;
    // Integer dislikeCount;
    // Integer favoriteCount;
    // Integer commentCount;

    public class ImageDetail {

        String url;
        Integer width;
        Integer height;

    }

    public class Image {

        ImageDetail primary;
        ImageDetail medium;
        ImageDetail high;

    }

}