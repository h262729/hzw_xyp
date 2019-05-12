package com.hzw.base.test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Test001 {

    @Test
    public void test003(){
       String str = ":1:2:222:";
       String[] array = str.split(":");
        System.out.println("array = " + array.length);
        System.out.println("array = " + JSONArray.toJSONString(array));
    }

    @Test
    public void test002(){
        Object[] keyAndValues = new Object[2];
        for(int i = 0; i < keyAndValues.length; i+=2){
            System.out.println("i = " + i);
            Object obj = keyAndValues[i+1];
        }
    }

    @Test
    public void test001(){
        JSONObject json = new JSONObject();
        json.put("name", "年时");
        json.put("name", "年时223");
        json.put("id", 123);
        json.put("arrays", new Integer[]{1,2,3});

        Map<String, Object> params = new HashMap<>();
        params.put("num", 22);
        params.put("page", 32);

        json.put("params", params);
        json.put("age", "未知");

        Map<String, Object> map = json.getInnerMap();
        for(String key : map.keySet()){
            System.out.println(key + " : " + map.get(key));
        }
    }

    private long startTime = 0L;
    private long endTime = 0L;
    @Before
    public void before(){
        System.out.println("## 开始测试");
        startTime = System.currentTimeMillis();
    }
    @After
    public void after(){
        endTime = System.currentTimeMillis();
        System.out.println("## 测试结束");
        System.out.println("## 测试用时：" + (endTime - startTime));

    }
}
