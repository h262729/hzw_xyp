package com.hzw.base.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hzw.base.tools.MapTools;
import com.hzw.base.tools.StringTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 基础controller，对一些常用的请求操作进行封装
 */
public class BaseAction {
    private static final Logger logger = LoggerFactory.getLogger(BaseAction.class);

    /**
     * 获取request参数数据 --返回String
     */
    public String getParam(HttpServletRequest request, String key){
        return request.getParameter("key");
    }

    /**
     * 获取request参数数据 --返回String
     */
    public String getParam(HttpServletRequest request, String key, String defaultValue){
        return StringTools.isEmpty(request.getParameter(key))? defaultValue : request.getParameter(key);
    }

    /**
     * 获取request参数数据 --返回int
     */
    public int getParam(HttpServletRequest request, String key, int defaultValue){
        try{
            return StringTools.isEmpty(request.getParameter(key))? defaultValue : Integer.valueOf(request.getParameter(key));
        }catch (Exception e){
            return defaultValue;
        }
    }

    /**
     * 获取request参数数据 --返回long
     */
    public Long getParamByLong(HttpServletRequest request, String key, Long defaultValue){
        try{
            return StringTools.isEmpty(request.getParameter(key))? defaultValue : Long.valueOf(request.getParameter(key));
        }catch (Exception e){
            return defaultValue;
        }
    }

    /**
     * 获取request参数数据 --返回double
     */
    public double getParam(HttpServletRequest request, String key, double defaultValue){
        try {
            return StringTools.isEmpty(request.getParameter(key))? defaultValue : Double.valueOf(request.getParameter(key));
        }catch (Exception e){
            return defaultValue;
        }
    }

    /**
     * 获取request参数数据 --返回jsonObject
     */
    public JSONObject getParam(HttpServletRequest request, String key, JSONObject defaultValue){
        try{
            String value = request.getParameter(key);
            return StringTools.isEmpty(value)? defaultValue : JSONObject.parseObject(value);
        }catch (Exception e){
            return defaultValue;
        }
    }

    /**
     * 获取request参数数据 --返回jsonArray
     */
    public JSONArray getParam(HttpServletRequest request, String key, JSONArray defaultValue){
        try{
            return StringTools.isEmpty(request.getParameter(key))? defaultValue : JSONArray.parseArray(request.getParameter(key));
        }catch (Exception e){
            return defaultValue;
        }
    }

    /**
     * 获取post请求传入的数据
     */
    public JSONObject getPostData(HttpServletRequest request){
        Map<String, String[]> parameterMap = request.getParameterMap();
        JSONObject requestJson = MapTools.getRequestJson(parameterMap);
        if(requestJson == null){
            requestJson = new JSONObject();
        }else{
            requestJson = requestJson.getJSONObject("data");
            requestJson = requestJson == null? new JSONObject() : requestJson;
        }
        return requestJson;
    }

    public JSONObject getPostData(HttpServletRequest request, String key, JSONObject defaultValue){
        JSONObject value = this.getPostData(request).getJSONObject(key);
        return value == null? defaultValue : value;
    }

    public JSONArray getPostData(HttpServletRequest request, String key, JSONArray defaultValue){
        JSONArray value = this.getPostData(request).getJSONArray(key);
        return value == null? defaultValue : value;
    }

    public String getPostData(HttpServletRequest request, String key, String defaultValue){
        String value = this.getPostData(request).getString(key);
        return value == null? defaultValue : value;
    }

    public Long getPostData(HttpServletRequest request, String key, long defaultValue){
        Long value = this.getPostData(request).getLong(key);
        return value == null? defaultValue : value;
    }

    public double getPostData(HttpServletRequest request, String key, double defaultValue){
        Double value = this.getPostData(request).getDouble(key);
        return value == null? defaultValue : value;
    }
}
