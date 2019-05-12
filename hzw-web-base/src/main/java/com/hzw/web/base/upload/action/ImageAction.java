package com.hzw.web.base.upload.action;

import com.alibaba.fastjson.JSONObject;
import com.hzw.web.base.upload.service.BImageService;
import com.hzw.base.controller.BaseAction;
import com.hzw.base.controller.ResponseResult;
import com.hzw.base.tools.others.RandomValidateCodeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 图片接口
 */
@RestController
@RequestMapping(value = "/api/base/image")
public class ImageAction extends BaseAction {
    private static final Logger logger = LoggerFactory.getLogger(ImageAction.class);

    /**
     * 验证码获取
     */
    @RequestMapping(value = "/verify", method = RequestMethod.GET)
    public void getVerify(HttpServletRequest request, HttpServletResponse response) throws Exception {

        response.setContentType("image/png");//设置相应类型,告诉浏览器输出的内容为图片
        response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expire", 0);
        RandomValidateCodeUtil util = new RandomValidateCodeUtil();
        util.getVerifyCode(request, response);//输出验证码图片方法
    }

    /**
     */
    /**
     * 图片上传
     * @param request
     * @param response
     * @param target    上传目标对象
     * @param targetId  上传目标对象id
     * @param mode  上传文件的存储模式
     * @param file  上传文件
     * @throws Exception
     */
    @PostMapping("/upload")
    public void uploadImage(HttpServletRequest request, HttpServletResponse response,
                            @RequestParam(value = "target") String target,
                            @RequestParam(value = "targetId") Long targetId,
                            @RequestParam(value = "mode") Integer mode,
                            @RequestParam(value = "file") MultipartFile file) throws Exception {
        logger.info("---- 图片上传开始 ----");
        JSONObject result = service.upload(file, target, targetId, mode);
        ResponseResult.create()
                .put("imgData", result)
                .put("message", "图片上传成功")
                .print(response);
    }

    @Autowired
    private BImageService service;
}
