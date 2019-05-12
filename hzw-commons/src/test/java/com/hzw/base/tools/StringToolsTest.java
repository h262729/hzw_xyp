package com.hzw.base.tools;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class StringToolsTest {

    /**
     * 测试 --下划线转驼峰式
     */
    @Test
    public void underlineToHumpCaseTest(){
        String str = StringTools.underlineToHumpCase("my_name _ is_hzw haha");
        System.out.println("转换结果：" + str);
    }

    /**
     * 测试 --驼峰式转下划线
     */
    @Test
    public void humpCaseToUnderline(){
        String str = StringTools.humpCaseToUnderline("myName HaHa youNameIsUncle");
        System.out.println("转换结果：" + str);
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
