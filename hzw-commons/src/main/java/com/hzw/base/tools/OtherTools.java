package com.hzw.base.tools;

import com.hzw.base.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 工具类 -- 暂时不知道要分到哪里的通用方法
 */
public class OtherTools {
    private static Logger logger = LoggerFactory.getLogger(OtherTools.class);

    /**
     * 将下划线式的key 转换成 驼峰式
     * 目前主要用在列表查询
     * @param rows
     */
    public static void underlineToHumpCase(List<Map<String, Object>> rows){
        if(rows == null || rows.isEmpty()){
            return;
        }
        for(int i = 0; i < rows.size(); i++){
            Map<String, Object> row = rows.get(i);
            row = underlineToHumpCase(row);
            rows.set(i, row);   // 替换掉原先的数据
        }
    }

    public static Map<String, Object> underlineToHumpCase(Map<String, Object> map){
        if(map == null || map.isEmpty()){
            return map;
        }
        Map<String, Object> newMap = new HashMap<String, Object>();
        for(String key : map.keySet()){
            String newKey = StringTools.underlineToHumpCase(key);
            newMap.put(newKey, map.get(key));
        }
        return newMap;
    }


    /**
     * 正则验证
     * @param param 验证参数
     * @param regex 正则表达式
     * @param errorMsg  返回的错误信息
     */
    public static void regexValidate(String param, String regex, String errorMsg){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(param);
        if(!matcher.matches()){
            throw new BusinessException(errorMsg);
        }
    }

    public static boolean regexValidate(String param, String regex){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(param);
        return matcher.matches();
    }
}
