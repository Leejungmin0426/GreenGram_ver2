package com.green.greengramver2.feed.comments;


import com.green.greengramver2.feed.comments.model.FeedCommentPostReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class FeedCommentService {
    private final FeedCommentsMapper mapper;


    public long insCommentFeed(FeedCommentPostReq p) {
        long result = mapper.insCommentFeed(p);
        return p.getFeedCommentId();
    }
}


