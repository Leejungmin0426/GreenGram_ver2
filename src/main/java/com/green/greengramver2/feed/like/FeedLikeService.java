package com.green.greengramver2.feed.like;

import com.green.greengramver2.feed.like.model.FeedLikeReq;
import com.green.greengramver2.feed.model.FeedPostReq;
import com.green.greengramver2.feed.model.FeedPostRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class FeedLikeService {
    private final FeedLikeMapper mapper;
    public int feedLikeToggle(FeedLikeReq p) {
        int result = mapper.delFeedLike(p);
        if (result == 0){
            return mapper.insFeedLike(p);
        }
        return 0;
    }

}
