package io.swen90007sm2.app.model.pojo;

import java.util.Date;

/**
 * object holds version control info. Used for optimistic lock
 */
public class VersionInfoBean {
    Integer version;
    Date updateTime;

    public VersionInfoBean() {
    }

    public VersionInfoBean(Integer version, Date updateTime) {
        this.version = version;
        this.updateTime = updateTime;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
