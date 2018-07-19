package com.lun.mlm.dao;

import com.lun.mlm.model.*;

import javax.mail.Store;
import java.util.List;

/**
 * User:policy
 * Date:2018/6/12 19:54
 */
public interface MsgDao {
    List<ZmStore> listStore(String name, Integer start, Integer end);
    List<ZmTable> listTable(String num, String storeId, Integer start, Integer end);
    Integer countStore(String name);
    void addStore(ZmStore zmStore);
    void delStore(String storeId);
    Integer countTable(String num, String storeId);

    List<ZmBanner> listByStoreId(String storeId);
    List<ZmMsg> listByStoreIdAndTableId(String storeId, String tableId, Integer page);
    List<ZmMsg> listByStoreIdAndTableIdAndMsgId(String storeId, String tableId, String msgId, Integer page);
    List<ZmMsg> listMyMsgs(String userId, Integer page);
    List<ZmMsg> listMyReplyMsgs(String userId, Integer page);
    List<ZmFriend> listZmFriend(String userId, Integer page);
    void delZmFriend(String userId, String friendUserId);
    ZmUser getUserByOpenId(String openId);
    ZmUser getUserById(String id);
    void addZmUser(ZmUser zmUser);
    void addZmFriend(ZmFriend zmFriend);
    ZmFriend getZmFriend(String userId, String friendUserId);
    void msgAdd(String storeId, String tableId, String userId,  String msgId, String detail, String pic1, String pic2, String pic3);
    void updateZmUser(ZmUser zmUser);
    void comment(String msgId);
    void share(String msgId);
    void praize(String msgId);
    void addMsgPraize(ZmMsgPraize zmMsgPraize);
    Integer countMsgPraize(ZmMsgPraize zmMsgPraize);
}
