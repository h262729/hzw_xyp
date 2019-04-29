package com.hzw.xyp.base.tools.others;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EncryptUtilTest {

    @Test
    public void getMD5() throws Exception {
        String str = EncryptUtil.getMD5("1");
        System.out.println("str = " + str);
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
