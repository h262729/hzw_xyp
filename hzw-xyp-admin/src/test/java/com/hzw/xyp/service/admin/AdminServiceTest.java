package com.hzw.xyp.service.admin;

import com.alibaba.fastjson.JSONObject;
import com.hzw.base.dao.QueryResultData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminServiceTest {
    @Autowired
    private AdminService adminService;

    @Test
    public void login(){
        //adminService.login(null,null);
    }

    @Test
    public void queryTest(){
        JSONObject filter = new JSONObject();
        filter.put("ids", new Integer[]{3,5});
        //filter.put("name", "黄");
        QueryResultData resultDto = adminService.query(filter, 1, 3);
        System.out.println(JSONObject.toJSONString(resultDto, true));
        System.out.println("#################################");
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
