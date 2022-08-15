package io.swen90007sm2.app.model.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * base entity that contains public field for all
 *
 * @author xiaotian
 */
public abstract class BaseEntity implements Serializable {

    // TODO should be String and generate_uid(10)
    private Integer Id;
    /**
     * It is a good practice to record time fields
     */
    private Date createTime;
    private Date updateTime;

    private Boolean isDeleted;

    public BaseEntity() {
    }

    public BaseEntity(Date createTime, Date updateTime) {
        this.createTime = createTime;
        this.updateTime = updateTime;
        isDeleted = false;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }
}
