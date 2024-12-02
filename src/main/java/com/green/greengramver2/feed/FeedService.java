package com.green.greengramver2.feed;

import com.green.greengramver2.common.model.MyFileUtils;
import com.green.greengramver2.feed.comments.FeedCommentsMapper;
import com.green.greengramver2.feed.comments.model.FeedCommentDto;
import com.green.greengramver2.feed.comments.model.FeedCommentGetReq;
import com.green.greengramver2.feed.comments.model.FeedCommentGetRes;
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
    private final FeedCommentsMapper feedCommentMapper;
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


    public List<FeedGetRes> getFeedList(FeedGetReq p) {
        List<FeedGetRes> list = feedMapper.getFeedList(p);
        for (FeedGetRes res : list) {
            res.setPics(feedPicsMapper.selFeedPics(res.getFeedId()));
            //피드 당 댓글 4개

            FeedCommentGetReq commentGetReq = new FeedCommentGetReq();
            commentGetReq.setPage(1);
            commentGetReq.setFeedId(res.getFeedId());


            List<FeedCommentDto> commentList = feedCommentMapper.selFeedCommentList(commentGetReq);

            FeedCommentGetRes commentGetRes = new FeedCommentGetRes();
            commentGetRes.setCommentList(commentList);
            commentGetRes.setMoreComment(commentList.size() == 4);

            if (commentGetRes.isMoreComment()) {
                commentList.remove(commentList.size() - 1);
            }
            // 댓글이 4개 미만일 경우, 필요한 작업 처리
            res.setComment(commentGetRes);
        }
            return list; // 피드 리스트 반환

    }
}


