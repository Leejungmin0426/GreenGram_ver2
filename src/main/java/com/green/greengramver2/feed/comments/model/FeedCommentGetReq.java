package com.green.greengramver2.feed.comments.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.green.greengramver2.common.model.Paging;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.BindParam;

@Getter
@Setter
@Slf4j
public class FeedCommentGetReq {
    @Schema(title = "로그인 유저 PK", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private long feedId;
    private final static int DEFAULT_PAGE_SIZE = 20;
    private final static int FIRST_COMMENT_SIZE = 3;

    @Schema(example = "1", description = "Selected Page")
    private int page;
    @Schema(example = "20", description = "item count per page")
    private int size;
    @JsonIgnore
    private int startIdx;

    public void setPage(int page){
        if (page< 1 ) {
            return;
        } if (page == 1) {
            startIdx = 0;
            size =  FIRST_COMMENT_SIZE + 1;
            return;
        }
        startIdx = ((page- 2) * DEFAULT_PAGE_SIZE) + FIRST_COMMENT_SIZE;
        size = DEFAULT_PAGE_SIZE + 1;

    }
}