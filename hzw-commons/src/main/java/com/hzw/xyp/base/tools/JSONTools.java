package com.hzw.xyp.base.tools;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.hzw.xyp.base.controller.ResponseResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * JSON工具类
 */
public class JSONTools {

    public static JSON toJson(Object obj){
        Object parseObj = JSON.toJSON(obj);
        if(parseObj instanceof JSONObject){
            return (JSONObject) parseObj;
        }else if(parseObj instanceof JSONArray){
            return (JSONArray) parseObj;
        }else{
            return null;
        }
    }

    /**
     * JSONObject 转 Map
     * @param params
     * @return
     */
    public static Map<String, Object> jsonObjectToMap(JSONObject params){
        Map<String, Object> paramsMap = params.getInnerMap();
        return paramsMap;
    }

    /**
     * 数据过滤 --JSON格式
     */
    public static void filter(JSON json, Filter filter){
        if(json instanceof JSONObject){
            filterAsJSONObject((JSONObject)json, filter);
        }else if(json instanceof JSONArray){
            filterAsJSONArray((JSONArray)json, filter);

        }
    }

    /**
     * 数据过滤 --JSONObject格式
     */
    public static void filterAsJSONObject(JSONObject jsonObject, Filter filter){
        if(jsonObject == null || jsonObject.isEmpty()){
            return;
        }

        List<String> whites = filter.getWhites();
        List<String> blacks = filter.getBlacks();
        if(whites.size() > 0){
            // 准备删除的参数
            List<String> readyRemoves = new ArrayList<String>();
            for(String key : jsonObject.keySet()){
                if(whites.contains(key)){
                    Object value = jsonObject.get(key);
                    if(value instanceof JSON){
                        filter((JSON)value, filter);
                    }
                }else{
                    readyRemoves.add(key);
                }
            }
            // 删除白名单外的数据
            for(String key : readyRemoves){
                jsonObject.remove(key);
            }
        }

        if(blacks.size() > 0){
            // 删除黑名单上的数据
            for(String key : blacks){
                jsonObject.remove(key);
            }
            for(String key : jsonObject.keySet()){
                Object value = jsonObject.get(key);
                if(value instanceof JSON){
                    filter((JSON) value, filter);
                }
            }
        }
    }

    /**
     * 数据过滤 --JSONArray格式
     */
    public static void filterAsJSONArray(JSONArray jsonArray, Filter filter){
        for(int i = 0; jsonArray != null && i < jsonArray.size(); i++){
            Object value = jsonArray.get(i);
            if(value instanceof JSON){
                filter((JSON)value, filter);
            }
        }
    }

    /**
     * 内部类 --过滤名单
     * 优先级：白名单 > 黑名单
     */
    public static class Filter{
        private List<String> whites = new ArrayList<String>();  // 白名单, 保留
        private List<String> blacks = new ArrayList<String>();  // 黑名单，去除

        private Filter(){
        }

        public static Filter getInstance(){
            return new Filter();
        }

        /**
         * 白名单
         */
        public Filter whites(String... values){
            for(int i = 0; values != null && i < values.length; i++){
                this.whites.add(values[i]);
            }
            return this;
        }

        /**
         * 白名单
         */
        public Filter whites(List<String> values){
            if(values != null){
                this.whites.addAll(values);
            }
            return this;
        }

        /**
         * 黑名单
         */
        public Filter blacks(String... values){
            for(int i = 0; values != null && i < values.length; i++){
                this.blacks.add(values[i]);
            }
            return this;
        }

        /**
         * 黑名单
         */
        public Filter blacks(List<String> values){
            if(values != null){
                this.blacks.addAll(values);
            }
            return this;
        }

        public List<String> getWhites() {
            return whites;
        }

        public List<String> getBlacks() {
            return blacks;
        }
    }
}
