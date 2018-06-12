package com.lun.mlm.utils;

/**
 * User:policy
 * Date:2018/5/15 17:05
 */
public class IDGenerator {
    public static final String nextId() {
        StringBuilder sb = new StringBuilder();
        sb.append(System.currentTimeMillis());
        sb.append(Long.valueOf(StringUtil.randomNum(5)));
        return sb.toString();
    }

}
