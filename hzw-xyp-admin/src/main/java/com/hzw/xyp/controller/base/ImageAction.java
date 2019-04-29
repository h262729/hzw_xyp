package com.hzw.xyp.controller.base;

import com.hzw.xyp.base.tools.others.RandomValidateCodeUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 图片接口
 */
@RestController
@RequestMapping(value = "/api/base/image")
public class ImageAction {

    @RequestMapping(value = "/verify", method = RequestMethod.GET)
    public void getVerify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("image/png");//设置相应类型,告诉浏览器输出的内容为图片
        response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expire", 0);
        RandomValidateCodeUtil util = new RandomValidateCodeUtil();
        util.getVerifyCode(request, response);//输出验证码图片方法
    }
}
