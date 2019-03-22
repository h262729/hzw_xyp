package com.hzw.xyp.base.exception;

/**
 * 业务类异常枚举 --统一管理下
 */
public enum ResponseCodeEnum {
    UNKONW_ERROR(-1,"未知错误"),
    SUCCESS(200,"成功"),
    ERROR(0,"失败"),
    SQL_QUERY_FAIL(350,"数据查询失败"),
    SQL_SAVE_FAIL(350,"数据保存失败"),
    SQL_DELETE_FAIL(350,"数据删除失败"),
    ;

    private Integer code;
    private String msg;

    ResponseCodeEnum(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }}
