package com.lun.mlm.dao.impl;

import com.lun.mlm.dao.MsgDao;
import com.lun.mlm.model.ZmBanner;
import com.lun.mlm.model.ZmMsg;
import com.lun.mlm.model.ZmUser;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User:policy
 * Date:2018/6/12 19:55
 */
@Repository("msgDao")
public class MsgDaoImpl extends SqlMapClientDaoSupport implements MsgDao {
    @Override
    public List<ZmBanner> listByStoreId(String storeId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("storeId", storeId);
        return this.getSqlMapClientTemplate().queryForList("Msg_SqlMap.listBanner", map);
    }

    public List<ZmMsg> listByStoreIdAndTableId(String storeId, String tableId, Integer page) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("storeId", storeId);
        map.put("tableId", tableId);
        map.put("start", (page-1)*10);
        map.put("end", page*10);
        return this.getSqlMapClientTemplate().queryForList("Msg_SqlMap.list", map);
    }

    @Override
    public ZmUser getUserByOpenId(String openId) {
        Map<String, Object> map = new HashMap<>();
        map.put("openId", openId);
        return (ZmUser) this.getSqlMapClientTemplate().queryForObject("Msg_SqlMap.getUserByOpenId", map);
    }

    @Override
    public void addZmUser(ZmUser zmUser) {
        this.getSqlMapClientTemplate().insert("Msg_SqlMap.addZmUser", zmUser);
    }
}
