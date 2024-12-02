package com.green.greengramver2.feed.comments.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
@Schema(title = "피드 댓글 등록 요청")
public class FeedCommentPostReq {


    @Schema(description = "피드 Pk", example = "456", requiredMode = Schema.RequiredMode.REQUIRED)
    private long feedId;

    @Schema(description = "로그인한 유저 PK", example = "789", requiredMode = Schema.RequiredMode.REQUIRED)
    private long userId;

    @Schema(description = "댓글 내용 (추가/수정 시 필요)", example = "이 댓글은 정말 유익하네요!", requiredMode = Schema.RequiredMode.REQUIRED)
    private String comment;

    @JsonIgnore
    private long feedCommentId;

}