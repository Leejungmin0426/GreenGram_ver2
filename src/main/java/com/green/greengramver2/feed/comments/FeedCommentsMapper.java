package com.green.greengramver2.feed.comments;

import com.green.greengramver2.feed.comments.model.FeedCommentDto;
import com.green.greengramver2.feed.comments.model.FeedCommentGetReq;
import com.green.greengramver2.feed.comments.model.FeedCommentPostReq;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FeedCommentsMapper {

    long insCommentFeed (FeedCommentPostReq p);


    List<FeedCommentDto> selFeedCommentList(FeedCommentGetReq p);

}
