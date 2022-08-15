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

    // should be String/CHAR(32) and generate_uid(32)
    private String id;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseEntity that = (BaseEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
