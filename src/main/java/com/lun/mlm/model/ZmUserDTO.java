package com.lun.mlm.model;

/**
 * User:policy
 * Date:2018/6/30 16:25
 */
public class ZmUserDTO extends ZmUser {
    private Integer commantCount;
    private Integer shareCount;
    private Integer praizeCount;

    public Integer getCommantCount() {
        return commantCount;
    }

    public void setCommantCount(Integer commantCount) {
        this.commantCount = commantCount;
    }

    public Integer getShareCount() {
        return shareCount;
    }

    public void setShareCount(Integer shareCount) {
        this.shareCount = shareCount;
    }

    public Integer getPraizeCount() {
        return praizeCount;
    }

    public void setPraizeCount(Integer praizeCount) {
        this.praizeCount = praizeCount;
    }
}
