package com.hzw.xyp.base.tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String 自定义封装工具类
 */
public class StringTools {

    /**
     * 判空
     * @param str
     * @return
     */
    public static boolean isEmpty(String str){
        return str == null || str.trim().length() == 0;
    }

    /**
     * 判非空
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str){
        return str != null && str.trim().length() > 0;
    }

    /**
     * 判断是否相等
     * @param str01
     * @param str02
     * @return
     */
    public static boolean isEqual(String str01, String str02){
        if(str01 == null && str02 == null){
            return true;
        }
        if(str01 != null && str01.equals(str02)){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 带有下划线的字符串转换成驼峰式
     * 例: my_name > myName
     * @param str
     * @return
     */
    public static String underlineToHumpCase(String str){
        if(isEmpty(str)){
            return str;
        }
        String[] strArrays = str.split("_");
        String newStr = "";
        for(int i = 0; i < strArrays.length; i++){
            if(i != 0 && isNotEmpty(strArrays[i])){
                newStr += strArrays[i].substring(0,1).toUpperCase() + strArrays[i].substring(1);
            }else{
                newStr += strArrays[i];
            }
        }
        return newStr;
    }

    /**
     * 驼峰转下划线 --使用正则
     * @param str
     * @return
     */
    public static String humpCaseToUnderline(String str){
        if(isEmpty(str)){
            return str;
        }
        Matcher matcher = Pattern.compile("[A-Z]").matcher(str);
        StringBuffer sb = new StringBuffer();
        while(matcher.find()){
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
}
