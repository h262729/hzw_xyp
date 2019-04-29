package com.hzw.xyp.base.constant;

/**
 * 正则表达式管理
 */
public class RegexManage {

    // 用户名
    public final static String USER_NAME = "[a-zA-Z0-9]{1,20}";
    // 密码
    public final static String PASSWORD = "(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z0-9]{6,20}";
    // 邮箱
    public final static String EMAIL = "[A-Za-z\\d]+([-_.][A-Za-z\\d]+)*@([A-Za-z\\d]+[-.])+[A-Za-z\\d]{2,4}";
}
