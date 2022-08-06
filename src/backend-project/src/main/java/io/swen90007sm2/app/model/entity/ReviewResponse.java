package io.swen90007sm2.app.model.entity;

import java.util.Date;

public class ReviewResponse extends BaseEntity {
    private String responseId;
    private String reviewId;
    private String userId;
    private String content;

    public ReviewResponse() {
    }

    public ReviewResponse(Date createTime, Date updateTime) {
        super(createTime, updateTime);
    }

    public ReviewResponse(String responseId, String reviewId, String userId, String content) {
        this.responseId = responseId;
        this.reviewId = reviewId;
        this.userId = userId;
        this.content = content;
    }

    public ReviewResponse(Date createTime, Date updateTime, String responseId, String reviewId, String userId, String content) {
        super(createTime, updateTime);
        this.responseId = responseId;
        this.reviewId = reviewId;
        this.userId = userId;
        this.content = content;
    }

    public String getResponseId() {
        return responseId;
    }

    public void setResponseId(String responseId) {
        this.responseId = responseId;
    }

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "ReviewResponse{" +
                "responseId='" + responseId + '\'' +
                ", reviewId='" + reviewId + '\'' +
                ", userId='" + userId + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
