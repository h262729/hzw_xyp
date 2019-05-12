package com.hzw.base.tools.others;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;

/**
 * 工具类 --- 加密
 */
public class EncryptUtil {
    private static Logger logger = LoggerFactory.getLogger(EncryptUtil.class);

    // md5加密 -- 碘盐
    private final static String MD5_SALT = "HZW-DEV";

    /**
     * 获取md5密文
     * @param str
     * @return
     * @throws Exception
     */
    public static String getMD5(String str) throws Exception {
        str += MD5_SALT; // 加碘盐
        return getMD5(str.getBytes());
    }

    /**
     * 获取md5密文
     */
    private static char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
    public static String getMD5(byte[] bytes) throws Exception {
        // 获取实例
        MessageDigest digest = MessageDigest.getInstance("MD5");
        // 加密
        digest.update(bytes);
        // 密文转换
        byte[] md5Bytes = digest.digest();
        char[] encryptText = new char[md5Bytes.length * 2];
        int k = 0;
        for (int i = 0; i < md5Bytes.length; i++) {
            byte byte0 = md5Bytes[i];
            encryptText[k++] = hexDigits[byte0 >>> 4 & 0xf];
            encryptText[k++] = hexDigits[byte0 & 0xf];
        }
        return new String(encryptText);
    }
}
