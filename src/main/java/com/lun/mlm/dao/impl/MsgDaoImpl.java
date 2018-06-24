package com.lun.mlm.dao.impl;

import com.lun.mlm.dao.MsgDao;
import com.lun.mlm.model.ZmBanner;
import com.lun.mlm.model.ZmFriend;
import com.lun.mlm.model.ZmMsg;
import com.lun.mlm.model.ZmUser;
import com.lun.mlm.utils.IDGenerator;
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
    public List<ZmMsg> listByStoreIdAndTableIdAndMsgId(String storeId, String tableId, String msgId, Integer page) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("storeId", storeId);
        map.put("tableId", tableId);
        map.put("msgId", msgId);
        map.put("start", (page-1)*10);
        map.put("end", page*10);
        return this.getSqlMapClientTemplate().queryForList("Msg_SqlMap.list", map);
    }

    @Override
    public ZmUser getUserByOpenId(String openId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("openId", openId);
        return (ZmUser) this.getSqlMapClientTemplate().queryForObject("Msg_SqlMap.getUserByOpenId", map);
    }

    @Override
    public void addZmUser(ZmUser zmUser) {
        this.getSqlMapClientTemplate().insert("Msg_SqlMap.addZmUser", zmUser);
    }

    @Override
    public void addZmFriend(ZmFriend zmFriend) {
        this.getSqlMapClientTemplate().insert("Msg_SqlMap.addZmFriend", zmFriend);
    }

    @Override
    public ZmFriend getZmFriend(String userId, String friendUserId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("friendUserId", friendUserId);
        return (ZmFriend) this.getSqlMapClientTemplate().queryForObject("Msg_SqlMap.getFriend", map);
    }

    @Override
    public void msgAdd(String storeId, String tableId, String userId, String msgId, String detail, String pic1, String pic2, String pic3) {
        ZmMsg zmMsg = new ZmMsg();
        zmMsg.setId(IDGenerator.nextId());
        zmMsg.setStore_id(storeId);
        zmMsg.setTable_id(tableId);
        zmMsg.setUser_id(userId);
        zmMsg.setMsg_id(msgId);
        zmMsg.setDetail(detail);
        zmMsg.setPic1(pic1);
        zmMsg.setPic2(pic2);
        zmMsg.setPic3(pic3);
        this.getSqlMapClientTemplate().insert("Msg_SqlMap.msgAdd", zmMsg);
    }

    @Override
    public void updateZmUser(ZmUser zmUser) {
        this.getSqlMapClientTemplate().update("Msg_SqlMap.updateZmUser", zmUser);
    }
}
