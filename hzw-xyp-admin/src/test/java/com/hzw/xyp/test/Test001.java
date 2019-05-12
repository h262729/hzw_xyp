package com.hzw.xyp.test;

import com.hzw.base.constant.RegexManage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test001 {
    private Logger logger = LoggerFactory.getLogger(Test001.class);

    @Test
    public void testValidatePwd(){
        String pwd = "qq@qq.com";

        // 正则验证
        String regex = RegexManage.EMAIL;
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(pwd);
        logger.info("验证结果：" + matcher.matches());
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
