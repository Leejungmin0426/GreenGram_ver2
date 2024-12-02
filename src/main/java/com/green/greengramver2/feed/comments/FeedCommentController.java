package com.green.greengramver2.feed.comments;

import com.green.greengramver2.common.ResultResponse;
import com.green.greengramver2.feed.comments.model.FeedCommentGetReq;
import com.green.greengramver2.feed.comments.model.FeedCommentGetRes;
import com.green.greengramver2.feed.comments.model.FeedCommentPostReq;
import com.green.greengramver2.feed.model.FeedGetReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("feed/comment")
@RequiredArgsConstructor
@Slf4j
public class FeedCommentController {
    private final FeedCommentService service;


    @PostMapping
    public ResultResponse<Long> insCommentFeed(@RequestBody FeedCommentPostReq p) {
        long result = service.insCommentFeed(p);
        return ResultResponse.<Long>builder()
                .resultMessage("댓글 등록 완료")
                .resultData(result)
                .build();
    }

//    @GetMapping("/list")
//    public ResultResponse<List<FeedCommentGetRes>> selFeedCommentList(@ParameterObject @ModelAttribute FeedCommentGetReq p) {
//        List<FeedCommentGetRes> comments = service.selFeedCommentList(p);
//        return ResultResponse.<List<FeedCommentGetRes>>builder()
//                .resultMessage("댓글 리스트 조회 성공")
//                .resultData(comments)
//                .build();
//    }
}

