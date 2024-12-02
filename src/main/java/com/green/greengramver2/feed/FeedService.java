package com.green.greengramver2.feed;

import com.green.greengramver2.common.ResultResponse;
import com.green.greengramver2.common.model.MyFileUtils;
import com.green.greengramver2.feed.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FeedService {
    private final FeedMapper feedMapper;
    private final FeedPicsMapper feedPicsMapper;
    private final MyFileUtils myFileUtils;

    @Transactional
    public FeedPostRes postFeed(List<MultipartFile> pics, FeedPostReq p) {

        int result = feedMapper.insFeed(p);


        // ---------------------------파일 등록


        long feedId = p.getFeedId();


        // -------------------------- 저장 폴더 만들기
        String middlePath = String.format("feed/%d", feedId);
        myFileUtils.makeFolders(middlePath);

        List<String> picNameList = new ArrayList<>(pics.size());

        for (MultipartFile pic : pics) {
            String savePicName = myFileUtils.makeRandomFileName(pic);
            String filePath = String.format("%s/%s", middlePath, savePicName);
            picNameList.add(savePicName);
            try {
                myFileUtils.transferTo(pic, filePath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

        FeedPicDto feedPicDto = new FeedPicDto();

        feedPicDto.setPics(picNameList);
        feedPicDto.setFeedId(feedId);

        int resultPics = feedPicsMapper.insFeedPics(feedPicDto);

        return FeedPostRes.builder()
                .feedId(feedId)
                .pics(picNameList)
                .build();

    }


    public void likeFeed(long feedId, long userId) {
        // 좋아요 중복 확인
        int count = feedMapper.checkLikeExists(feedId, userId);
        if (count == 0) {
            feedMapper.insertLike(feedId, userId); // 좋아요 추가
        } else {
            throw new IllegalStateException("이미 좋아요를 누른 상태입니다.");
        }
    }





    public List<FeedGetRes> selFeedList (FeedGetReq p) {
        List<FeedGetRes> list = feedMapper.selFeedList(p);
        for (FeedGetRes res : list) {
            List<String> picList = feedPicsMapper.selFeedPics(res.getFeedId());
            res.setPics(picList);
        }
        return list;
    }



    public void createFeed(FeedPostReq feedRequest) {
        // 피드 생성 로직
        System.out.println("Creating feed: " + feedRequest);
    }

}
