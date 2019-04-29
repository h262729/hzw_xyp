package com.hzw.xyp.base.tools;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 时间格式化工具类
 */
public class DateTools {
    public static final String format1="yyyy-MM-dd";
    public static final String format2="yyyy-MM-dd HH:mm:ss";
    public static final String format3="yyyy-MM-dd HH:mm";

    public static final SimpleDateFormat dateFormat1 = new SimpleDateFormat(format1);
    public static final SimpleDateFormat dateFormat2 = new SimpleDateFormat(format2);
    public static final SimpleDateFormat dateFormat3 = new SimpleDateFormat(format3);

    private static final Map<String, SimpleDateFormat> formats = new HashMap<String, SimpleDateFormat>();

    static{
        formats.put(format1, dateFormat1);
        formats.put(format2, dateFormat2);
        formats.put(format3, dateFormat3);
    }

    /**
     * 返回时间字符串 --默认格式：yyyy-MM-dd HH:mm:ss
     */
    public static String toString(Date date){
        return date != null? dateFormat2.format(date) : null;
    }

    /**
     * 返回时间字符串 --指定格式
     */
    public static String toString(Date date, String formatStr){
        if(StringTools.isEmpty(formatStr)){
            return toString(date);
        }else{
            SimpleDateFormat simpleDateFormat = formats.get(formatStr);
            if(simpleDateFormat == null){
                simpleDateFormat = new SimpleDateFormat(formatStr);
            }
            return simpleDateFormat.format(date);
        }
    }

}
