package com.green.greengramver2.feed;

import com.green.greengramver2.common.ResultResponse;
import com.green.greengramver2.common.model.MyFileUtils;
import com.green.greengramver2.feed.model.FeedPostReq;
import com.green.greengramver2.feed.model.FeedPostRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FeedService {
    private final FeedMapper feedmapper;
    private final FeedPicsMapper feedPicsMapper;
    private final MyFileUtils myFileUtils;

    public FeedPostRes postFeed(List<MultipartFile> pics, FeedPostReq p) {

        int result = feedmapper.insFeed(p);


        // ---------------------------파일 등록


        long feedId = p.getFeedId();


        // -------------------------- 저장 폴더 만들기
        String middlePath = String.format("feed/%d", feedId);
        myFileUtils.makeFolders(middlePath);


        for(MultipartFile pic : pics){
            String savePicName = myFileUtils.makeRandomFileName(pic);
            String filePath = String.format("%s%s", middlePath, savePicName);


            try {
                myFileUtils.transferTo(pic, filePath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

        return null;
    }


}
