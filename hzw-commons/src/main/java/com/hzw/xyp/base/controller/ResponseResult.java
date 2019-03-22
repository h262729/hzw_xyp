package com.hzw.xyp.base.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hzw.xyp.base.tools.DateTools;
import com.hzw.xyp.base.tools.JSONTools;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 请求响应 --封装
 */
public class ResponseResult {

    private JSONObject dataJson;    // 返回的数据
    private Integer code;   // 状态码
    private List<String> whites = new ArrayList<String>();  // 白名单，允许返回的数据
    private List<String> blacks = new ArrayList<String>();  // 黑名单，不允许返回的数据

    public ResponseResult(){
        dataJson = null;
    }

    public ResponseResult(Object bean){
        JSON beanJson = JSONTools.toJson(bean);
        if(beanJson instanceof JSONArray){
            dataJson = new JSONObject();
            dataJson.put("array", beanJson);
        }else if(beanJson instanceof JSONObject){
            dataJson = (JSONObject)beanJson;
        }else{
            dataJson = null;
        }
    }

    public static ResponseResult create(){
        return new ResponseResult();
    }

    public static ResponseResult create(Object bean){
        return new ResponseResult(bean);
    }

    public ResponseResult put(String key, Object obj){
        dataJson.put(key, obj);
        return this;
    }

    /**
     * 白名单
     */
    public ResponseResult whites(String... values){
        for(int i = 0; values != null && i < values.length; i++){
            whites.add(values[i]);
        }
        return this;
    }

    /**
     * 黑名单
     */
    public ResponseResult blacks(String... values){
        for(int i = 0; values != null && i < values.length; i++){
            blacks.add(values[i]);
        }
        return this;
    }

    /**
     * 设置状态码
     */
    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     * 获取返回数据的字符串格式
     */
    public String getDataText(){
        if(dataJson == null){
            return null;
        }
        // 黑白名单过滤
        JSONTools.filter(dataJson, JSONTools.Filter.getInstance().whites(this.whites).blacks(this.blacks));
        return dataJson.toJSONString();
    }

    //输出
    public void print(HttpServletResponse response) throws Exception{
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        response.setHeader("Cache-Control", "no-cache");
        if(this.code != null) {//设定状态码表
            response.setStatus(this.code);
        }
        String responseText = this.getDataText();
        System.out.println(responseText);
        Date now = new Date();
        response.setHeader("Server-Now", DateTools.toString(now));
        response.getWriter().print(responseText);
    }
}
