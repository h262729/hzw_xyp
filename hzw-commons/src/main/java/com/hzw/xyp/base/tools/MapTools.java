package com.hzw.xyp.base.tools;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;

import java.util.Map;

public class MapTools {

    /**
     * map转String类型，目前主要用于request请求的body数据处理
     */
    public static JSONObject getRequestJson(Map<String, String[]> map) {
        JSONObject json = new JSONObject();
        if(map == null || map.isEmpty()){
            return json;
        }
        for(String key : map.keySet()){
            String[] value = map.get(key);
            if(value != null && value.length == 1){

                json.put(key, JSONTools.objToJson(value[0]));
            }else{
                json.put(key, value);
            }
        }
        return json;
    }
}
