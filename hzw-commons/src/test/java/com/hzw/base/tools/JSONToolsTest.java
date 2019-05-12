package com.hzw.base.tools;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JSONToolsTest {
    private static final Logger logger = LoggerFactory.getLogger(JSONToolsTest.class);

    @Test
    public void test03(){
        String str = "[1,23,4]";
        JSON json = JSONTools.objToJson(str);
        if(json instanceof JSONObject){
            JSONObject jsonObject = (JSONObject) json;
            logger.info("结果22:" + jsonObject );
        }else{
            logger.info("结果444:" + null );
        }

    }

    @Test
    public void test02(){
        JSONObject json = new JSONObject();
        json.put("name", "不知道");
        json.put("name2", "不知道222");
        json.put("name3", "不知道333");
        json.put("name4", "不知道444");
        JSONObject json2 = new JSONObject();
        json2.put("name", "你猜猜");
        json2.put("name2", "你猜猜222");
        json2.put("name3", "你猜猜333");
        JSONArray array = new JSONArray();
        array.add(1);
        array.add("222");
        array.add("333");
        array.add(json2);
        json.put("name6", array);

        JSONTools.filter(json, JSONTools.Filter.getInstance().blacks("name", "name2"));

        System.out.println("=== 输出结果 ===");
        System.out.println(json.toJSONString());

        JSONObject json3 = new JSONObject();
        json3.put("name", "你阿萨德猜猜");
        json3.put("name2", "你阿萨德猜猜222");
        json3.put("name3", "你阿萨德猜猜333");
        JSONTools.filter(json3, JSONTools.Filter.getInstance().blacks("name3"));

        System.out.println("=== 输出结果22 ===");
        System.out.println(json3.toJSONString());
    }

    @Test
    public void test01(){
        String jsonStr = "[1,2,3,{'aa':'bb'}]";
        JSON json = JSONTools.objToJson(jsonStr);
        System.out.println(json.toJSONString());
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
