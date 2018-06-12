package com.lun.mlm.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Enumeration;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class StringUtil {
    private final static Log log = LogFactory.getLog(StringUtil.class);
	public static String randomNum(int length) {
		Random ram = new Random();
		int inum = Math.abs(ram.nextInt());
		String numt = String.valueOf(inum);
		StringBuffer sbbase = new StringBuffer("0");

		for(int i = 0; i < length; ++i) {
			sbbase.append("0");
		}

		String sbase = sbbase.toString();
		String snum = null;
		if(numt.length() < length) {
			snum = sbase.substring(0, length - numt.length()) + numt;
		} else {
			snum = numt.substring(0, length);
		}

		return snum;
	}

    public static String encodePassword(String password, String algorithm) {
        byte[] unencodedPassword = password.getBytes();

        MessageDigest md = null;

        try {
            // first create an instance, given the provider
            md = MessageDigest.getInstance(algorithm);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return password;
        }

        md.reset();

        // call the update method one or more times
        // (useful when you don't know the size of your data, eg. stream)
        md.update(unencodedPassword);

        // now calculate the hash
        byte[] encodedPassword = md.digest();

        StringBuffer buf = new StringBuffer();

        for (int i = 0; i < encodedPassword.length; i++) {
            if ((encodedPassword[i] & 0xff) < 0x10) {
                buf.append("0");
            }
            buf.append(Long.toString(encodedPassword[i] & 0xff, 16));
        }

        return buf.toString();
    }

	public static String uuid() {
		java.util.UUID uuid = java.util.UUID.randomUUID();
		return uuid.toString().replaceAll("-", "");
	}
	

    public static String md5(String text) {
        String result = "";
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            byte[] buf = text.getBytes();
            byte[] dig = m.digest(buf);
            String hex = null;
            for (int i = 0; i < dig.length; i++) {
                int n = dig[i] < 0 ? (256 + dig[i]) : dig[i];
                hex = Integer.toHexString(n);
                if (hex.length() < 2)
                    hex = "0" + hex;
                result += hex;
            }
        } catch (NoSuchAlgorithmException e) {
            //e.printStackTrace();
            result = null;
        }
        return result;
    }
    
    public static String sha1(String str) {
        if (str == null) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
            messageDigest.update(str.getBytes());
            return getFormattedText(messageDigest.digest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    
	// 返回十六进制字符串
	private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	private static String getFormattedText(byte[] bytes) {
		int len = bytes.length;
		StringBuilder buf = new StringBuilder(len * 2);
		// 把密文转换成十六进制的字符串形式
		for (int j = 0; j < len; j++) {
			buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
			buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
		}
		return buf.toString();
	}
    
    /**
	 * 计算传入的字符串的字节数 按英文数字算�?��字节，中文及全角符号算两个字�?
	 * 
	 * @param arg
	 * @return int
	 */
	public static int calcByteCount(String arg) {
		if (arg != null && arg.length() > 0) {
			char[] chs = arg.toCharArray();
			int count = 0;
			for (int i = 0; i < chs.length; i++) {
				if (!((chs[i] >= '\uFF61') && (chs[i] <= '\uFF9F')) && !(('\u0020' <= chs[i]) && (chs[i] <= '\u007E'))) {
					count++;
				}
			}
			return (arg.length() + count);
		}
		return 0;
	}
    
    public static String toDecimalFormat(double number) {
    	NumberFormat formatter = new DecimalFormat("0.00");
    	return formatter.format(number);
    }
    
    /**
     * 根据给定长度获得随机数
     *
     * @param int
     * @return
     */
    public static String getRandomNum(int length) {
        Random ram = new Random();
        int inum = Math.abs(ram.nextInt());
        String numt = String.valueOf(inum);
        StringBuffer sbbase = new StringBuffer("0");
        for (int i = 0; i < length; i++) {
            sbbase.append("0");
        }
        String sbase = sbbase.toString();
        String snum = null;
        if (numt.length() < length) {
            snum = sbase.substring(0, length - numt.length()) + numt;
        } else {
            snum = numt.substring(0, length);
        }
        return snum;
    }
    
    public static String generateTransId() {
    	String base = "10000000";
		java.util.Calendar calendar = java.util.Calendar.getInstance();
    	BigDecimal current = new BigDecimal(String.valueOf(calendar.getTimeInMillis()));
    	calendar.set(2010, 7, 19, 0, 0, 0);
    	BigDecimal reference = new BigDecimal(String.valueOf(calendar.getTimeInMillis()));
    	BigDecimal re = current.subtract(reference).add(new BigDecimal(base));
    	String timePart = re.toString();
    	if(timePart.length() > base.length()) timePart = timePart.substring(0, base.length());
    	long time = System.nanoTime();
    	long thread = Thread.currentThread().getId();
    	long memory = Runtime.getRuntime().freeMemory();
    	int hashCode = getTransHash(time, thread, memory, getLocalIpAddress());
    	String lastPart = getLastPart(hashCode, 14 - base.length());
    	return timePart + lastPart;
    }
    
    private static int getTransHash(long time, long thread, long memory, String ipAddress) {
    	return new HashCodeBuilder().append(time).append(thread).append(memory).append(ipAddress).toHashCode();
    }
    
    private static String getLastPart(int number) {
    	return getLastPart(number, 8);
    }
    
    private static String getLastPart(int number, int digit) {
    	String result = null;
    	int hash = Math.abs(number);
    	String str = String.valueOf(hash);
    	int length = str.length();
    	if (length <= digit) {
    		result = lpad(str, digit, "0");
    	} else {
    		result = str.substring(length - digit, length);
    	}
    	return result;
    }
    
    private static String lpad(String str, int num, String pad) {
		String n_str = str;
		if (str == null) n_str = "";
		int length = calcByteCount(str);
		StringBuffer sb = new StringBuffer();
		for (int i = length; i < num; i++) {
			sb.append(pad);
		}
		sb.append(n_str);
		return sb.toString();
	}
    
    public static String getLocalIpAddress() {
    	String local = "127.0.0.1";
		// 根据网卡取本机配置的IP
		Enumeration netInterfaces;
		try {
			netInterfaces = NetworkInterface.getNetworkInterfaces();
			InetAddress ip = null;
			while (netInterfaces.hasMoreElements()) {
				NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();
				ip = (InetAddress) ni.getInetAddresses().nextElement();
				if (!ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1) {
					return ip.getHostAddress();
				}
			}

		} catch (Exception e) {
			return local;
		}
		return local;
	}
    
    public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean isNotBlank(String str) {
        return !StringUtil.isBlank(str);
    }
    
    /**
	 * 获得一个UUID
	 * @return String UUID
	 */
	public static String getUUID() {
		String s = UUID.randomUUID().toString();
		return s.replaceAll("-", "");
	}
	
	public static String getMD5(String text) {
		String result = "";
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] buf = text.getBytes();
			byte[] dig = md5.digest(buf);
			String hex = null;
			for (int i = 0; i < dig.length; i++) {
				int n = dig[i] < 0 ? (256 + dig[i]) : dig[i];
				hex = Integer.toHexString(n);
				if (hex.length() < 2)
					hex = "0" + hex;
				result += hex;
			}
		} catch (NoSuchAlgorithmException e) {
			// e.printStackTrace();
			result = null;
		}
		return result;
	}
	
	public static boolean isNumeric(String str){
		if(StringUtil.isBlank(str)) return false;
		  for (int i = 0; i < str.length(); i++){
		   if (!Character.isDigit(str.charAt(i))){
			   return false;
		   }
		  }
		  return true;
	 }
	
}
