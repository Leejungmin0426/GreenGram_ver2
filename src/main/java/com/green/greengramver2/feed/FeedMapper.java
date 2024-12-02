package com.green.greengramver2.feed;

import com.green.greengramver2.feed.model.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FeedMapper {
    int insFeed (FeedPostReq p);
    List<FeedGetRes> getFeedList (FeedGetReq p);
    int checkLikeExists(long feedId, long userId);
    void insertLike(long feedId, long userId);
}
