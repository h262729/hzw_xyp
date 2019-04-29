package com.hzw.xyp.controller.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hzw.xyp.base.constant.SessionManage;
import com.hzw.xyp.base.controller.BaseAction;
import com.hzw.xyp.base.controller.ResponseResult;
import com.hzw.xyp.base.exception.BusinessException;
import com.hzw.xyp.base.tools.JSONTools;
import com.hzw.xyp.beans.admin.Admin;
import com.hzw.xyp.service.admin.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * api接口 --登录/退出登录
 */
@RestController
@RequestMapping(value = "/api/xyp-admin/")
public class LoginAction extends BaseAction {
    private static final Logger logger = LoggerFactory.getLogger(LoginAction.class);

    /**
     * 登录
     */
    @PostMapping(value = "login")
    public void login(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("登陆请求");
        JSONObject postData = this.getPostData(request);
        JSONTools.validate(postData, "userName", "请输入用户名", "pwd", "请输入密码", "vCode", "请输入验证码");
        String userName = this.getPostData(request, "userName", "");
        String pwd = this.getPostData(request, "pwd", "");
        String vCode = this.getPostData(request, "vCode", "");

        HttpSession session = request.getSession();
        // 验证码验证
        String sessionValCode = (String) session.getAttribute(SessionManage.VALIDATA_CODE);
        if(!vCode.equalsIgnoreCase(sessionValCode)){
            throw new BusinessException("验证码错误!");
        }

        // 登录验证
        Admin bean = adminService.login(userName, pwd);

        // 记录session
        JSON beanJson = JSONTools.filterAsObject(bean, JSONTools.Filter.getInstance().blacks("pwd"));

        session.setAttribute(SessionManage.LOGIN_ADMIN, beanJson);
        session.setMaxInactiveInterval(60 * 60 * 3);    // 登录有效时间 单位：秒

        ResponseResult.create(beanJson)
                .whites("id", "name", "nickname", "imgs")
                .print(response);
    }

    /**
     * 注销、退出登录
     */
    @GetMapping("logout")
    public void test001(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        session.removeAttribute(SessionManage.LOGIN_ADMIN);
        ResponseResult.create().put("msg", "注销成功！").print(response);
    }

    @Autowired
    private AdminService adminService;

}
