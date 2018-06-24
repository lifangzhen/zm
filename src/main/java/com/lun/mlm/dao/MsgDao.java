package com.lun.mlm.dao;

import com.lun.mlm.model.ZmBanner;
import com.lun.mlm.model.ZmFriend;
import com.lun.mlm.model.ZmMsg;
import com.lun.mlm.model.ZmUser;

import java.util.List;

/**
 * User:policy
 * Date:2018/6/12 19:54
 */
public interface MsgDao {
    List<ZmBanner> listByStoreId(String storeId);
    List<ZmMsg> listByStoreIdAndTableId(String storeId, String tableId, Integer page);
    List<ZmMsg> listByStoreIdAndTableIdAndMsgId(String storeId, String tableId, String msgId, Integer page);
    ZmUser getUserByOpenId(String openId);
    ZmUser getUserById(String id);
    void addZmUser(ZmUser zmUser);
    void addZmFriend(ZmFriend zmFriend);
    ZmFriend getZmFriend(String userId, String friendUserId);
    void msgAdd(String storeId, String tableId, String userId,  String msgId, String detail, String pic1, String pic2, String pic3);

    void updateZmUser(ZmUser zmUser);
}
