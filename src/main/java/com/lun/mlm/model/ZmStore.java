package com.lun.mlm.model;

import java.util.Date;

/**
 * User:policy
 * Date:2018/5/28 0:33
 */
public class ZmStore {
    private String id;
    private String name;
    private Date create_at;
    private Date update_at;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}
