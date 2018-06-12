package com.lun.mlm.dao;

import com.lun.mlm.model.ZmBanner;
import com.lun.mlm.model.ZmMsg;

import java.util.List;

/**
 * User:policy
 * Date:2018/6/12 19:54
 */
public interface MsgDao {
    List<ZmBanner> listByStoreId(String storeId);
    List<ZmMsg> listByStoreIdAndTableId(String storeId, String tableId, Integer page);
}
