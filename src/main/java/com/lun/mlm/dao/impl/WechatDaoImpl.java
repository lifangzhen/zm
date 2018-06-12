package com.lun.mlm.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.lun.mlm.dao.WechatDao;
import com.lun.mlm.model.MsgTemplate;
import com.lun.mlm.model.WechatMenu;
import com.lun.mlm.model.WechatParam;

@Repository("wechatDao")
public class WechatDaoImpl extends SqlMapClientDaoSupport implements WechatDao {

	@Override
	public WechatParam getWechat(String id) {
		return (WechatParam) this.getSqlMapClientTemplate().queryForObject("Wx_SqlMap.getWechat", id);
	}

	@Override
	public void insertParam(WechatParam wp) {
		this.getSqlMapClientTemplate().insert("Wx_SqlMap.insertParam", wp);
	}

	@Override
	public void updateParam(WechatParam wp) {
		this.getSqlMapClientTemplate().update("Wx_SqlMap.updateParam", wp);
	}

	@Override
	public WechatMenu getMenu(String id) {
		return (WechatMenu) this.getSqlMapClientTemplate().queryForObject("Wx_SqlMap.getMenu", id);
	}

	@Override
	public void addMenu(WechatMenu menu) {
		this.getSqlMapClientTemplate().insert("Wx_SqlMap.addMenu", menu);
	}

	@Override
	public void updateMenu(WechatMenu menu) {
		this.getSqlMapClientTemplate().update("Wx_SqlMap.updateMenu", menu);
	}

	@Override
	public void deleteMenu(String id) {
		this.getSqlMapClientTemplate().delete("Wx_SqlMap.deleteMenu", id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WechatMenu> listMenu(Integer level, String father) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("level", level);
		map.put("father", father);
		return this.getSqlMapClientTemplate().queryForList("Wx_SqlMap.listMenu", map);
	}

}
