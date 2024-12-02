package com.green.greengramver2.feed.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FeedGetRes {
    private long feedId;
    private String contents;
    private String createdAt;
    private String location;
    private long writerUserId;
    private String writerNm;
    private String writerPic;


    private List<String> pics;

}