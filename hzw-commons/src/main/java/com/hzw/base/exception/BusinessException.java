package com.hzw.base.exception;

/**
 * 自定义业务异常
 */
public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private Integer code;

    public BusinessException(ResponseCodeEnum responseCodeEnum){
        this(responseCodeEnum.getCode(), responseCodeEnum.getMsg());
    }

    public BusinessException(String msg){
        super(msg);
        this.code = ResponseCodeEnum.BUSINESS_ERROR.getCode();
    }

    public BusinessException(Integer code, String msg){
        super(msg);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
