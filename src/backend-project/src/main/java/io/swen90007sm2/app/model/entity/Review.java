package io.swen90007sm2.app.model.entity;

import java.util.Date;

public class Review extends BaseEntity{

    private String reviewId;

    private String userId;

    private String hotelId;

    private String content;

    private Integer rank;

    public Review() {
    }

    public Review(Date createTime, Date updateTime) {
        super(createTime, updateTime);
    }

    public Review(String reviewId, String userId, String hotelId, String content, Integer rank) {
        this.reviewId = reviewId;
        this.userId = userId;
        this.hotelId = hotelId;
        this.content = content;
        this.rank = rank;
    }

    public Review(Date createTime, Date updateTime, String reviewId, String userId, String hotelId, String content, Integer rank) {
        super(createTime, updateTime);
        this.reviewId = reviewId;
        this.userId = userId;
        this.hotelId = hotelId;
        this.content = content;
        this.rank = rank;
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

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "Review{" +
                "reviewId='" + reviewId + '\'' +
                ", userId='" + userId + '\'' +
                ", hotelId='" + hotelId + '\'' +
                ", content='" + content + '\'' +
                ", rank=" + rank +
                '}';
    }
}
