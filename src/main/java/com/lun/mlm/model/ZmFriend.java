package com.lun.mlm.model;

import java.util.Date;

/**
 * User:policy
 * Date:2018/5/28 0:33
 */
public class ZmFriend {
    private String id;
    private String user_id;
    private String friend_user_id;
    private String name;
    private String head_img;
    private Date create_at;
    private Date update_at;
    private Integer status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getFriend_user_id() {
        return friend_user_id;
    }

    public void setFriend_user_id(String friend_user_id) {
        this.friend_user_id = friend_user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreate_at() {
        return create_at;
    }

    public void setCreate_at(Date create_at) {
        this.create_at = create_at;
    }

    public Date getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(Date update_at) {
        this.update_at = update_at;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
