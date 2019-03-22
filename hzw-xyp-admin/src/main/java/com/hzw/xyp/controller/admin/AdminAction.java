package com.hzw.xyp.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.hzw.xyp.base.controller.BaseAction;
import com.hzw.xyp.base.controller.ResponseResult;
import com.hzw.xyp.base.dao.QueryResultData;
import com.hzw.xyp.service.admin.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * api接口 --管理员
 */
@RestController
@RequestMapping(value = "/api/xyp-admin/admin")
public class AdminAction extends BaseAction {

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public void query(HttpServletRequest request, HttpServletResponse response) throws Exception {
        JSONObject filter = this.getParam(request, "filter", new JSONObject());
        Integer pageSize = this.getParam(request, "pageSize", 10);
        Integer pageNum = this.getParam(request, "pageNum", 1);
        QueryResultData result = service.query(filter, pageNum, pageSize);
        ResponseResult.create(result).print(response);
    }

    @Autowired
    private AdminService service;
}
