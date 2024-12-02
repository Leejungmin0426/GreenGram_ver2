package com.green.greengramver2.feed.comments.model;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Getter
@Setter
@Slf4j
public class FeedCommentGetRes { //페이징의 기본생성자가 없어서
 private boolean moreComment;

 private List<FeedCommentDto> commentList;

}
