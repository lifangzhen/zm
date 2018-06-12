package com.lun.mlm.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * 微信请求校验
 * SignUtil
 *
 * @author lfz
 * @createTime 2014-7-30 下午08:21:42
 * @modifyTime 2014-7-30 下午08:21:42
 */
public class SignUtil {
	
	//校验token码
	private static String TOKEN = "token";
	
	/**
	 * 签名校验
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @return
	 */
	public static boolean checkSignature(String signature, String timestamp, String nonce){
		//自然排序
		String[] paramArr = new String[]{TOKEN,timestamp,nonce};
		Arrays.sort(paramArr);
		
		//排序后的结果平成字符串
		String content = paramArr[0].concat(paramArr[1]).concat(paramArr[2]);
		
		String check = null;
		try{
			//将得到的字符串用SHA-1加密
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			byte[] digest = md.digest(content.getBytes());
			
			check = byteToStr(digest);
		}catch(NoSuchAlgorithmException e){
			e.printStackTrace();
		}
		
		// 返回校验结果
		return check != null?check.equals(signature.toUpperCase()):false;
	}
	
	/**
	 * 字节转为字符串
	 * 
	 * @param byteArray
	 * @return
	 */
	private static String byteToStr(byte[] byteArray) {
		String strDigest = "";
		for (int i = 0; i < byteArray.length; i++) {
			strDigest += byteToHexStr(byteArray[i]);
		}
		return strDigest;
	}

	/**
	 * 字节转为16进制
	 * 
	 * @param mByte
	 * @return
	 */
	private static String byteToHexStr(byte mByte) {
		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		char[] tempArr = new char[2];
		tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
		tempArr[1] = Digit[mByte & 0X0F];

		String s = new String(tempArr);
		return s;
	}
}
