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

    // Long snowflake, logic id for db
    private Long id;
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

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseEntity that = (BaseEntity) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
