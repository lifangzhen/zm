package com.lun.mlm.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class WechatMenu {
	public static final String TYPE_TEXT = "text";
	public static final String TYPE_IMAGE = "image";
	public static final String TYPE_VIEW = "view";
	public static final String TYPE_CLICK = "click";
	public static final Integer STATUS_NORMAL = 1;
	public static final Integer STATUS_DELETE = 0;
	
	public static final Integer LEVEL_ONE = 1;
	public static final Integer LEVEL_TWO = 2;
	
    private String id;//菜单id（包含展示顺序 如1001，1002，2001，3001）
    private Integer version;
    private Date createTime;
    private String name;
    private Integer level;//菜单等级（一级1，二级2）
    private Integer number;  //排列
    private String type;//菜单类型店卡内部链接，触发消息，外部链接
    private String content;//菜单内容，若是触发消息则是消息的模版id
    private String father;//父菜单id
    private Integer status; //0删除 1正常
    private String mediaId;
    private Date updateTime;
	private List<WechatMenu> childList;

	public List<WechatMenu> getChildList() {
		return childList;
	}

	public void setChildList(List<WechatMenu> childList) {
		this.childList = childList;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFather() {
		return father;
	}

	public void setFather(String father) {
		this.father = father;
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
    
}