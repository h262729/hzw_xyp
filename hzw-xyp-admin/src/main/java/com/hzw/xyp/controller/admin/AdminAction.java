package com.hzw.xyp.controller.admin;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hzw.xyp.base.controller.BaseAction;
import com.hzw.xyp.base.controller.ResponseResult;
import com.hzw.xyp.base.dao.QueryResultData;
import com.hzw.xyp.beans.admin.Admin;
import com.hzw.xyp.service.admin.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * api接口 --管理员
 */
@RestController
@RequestMapping(value = "/api/xyp-admin/admin")
public class AdminAction extends BaseAction {
    private static final Logger logger = LoggerFactory.getLogger(AdminAction.class);

    /**
     * 列表查询
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public void query(HttpServletRequest request, HttpServletResponse response) throws Exception {
        JSONObject filter = this.getParam(request, "filter", new JSONObject());
        Integer pageSize = this.getParam(request, "pageSize", 10);
        Integer pageNum = this.getParam(request, "pageNum", 1);
        QueryResultData result = service.query(filter, pageNum, pageSize);
        ResponseResult.create(result).blacks("pwd").print(response);
    }

    /**
     * 获取实体数据
     */
    @GetMapping("/get")
    public void get(HttpServletRequest request, HttpServletResponse response) throws Exception{
        long id = this.getParamByLong(request, "id", -1L);
        logger.info("获取实体数据：" + id);
        JSONObject beanJson = service.get(id);
        ResponseResult.create()
                .put("beanData", beanJson)
                .put("message", "获取成功")
                .print(response);
    }

    /**
     * 新增或保存
     */
    @PostMapping
    public void save(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("保存数据 ：" + JSONObject.toJSONString(this.getPostData(request)));
        JSONObject json = this.getPostData(request);
        JSONObject beanJson = service.save(json);
        ResponseResult.create()
                .put("beanData", beanJson)
                .put("message", "保存成功")
                .print(response);
    }

    /**
     * 重新初始化密码
     * @param request
     * @param response
     * @throws Exception
     */
    @PostMapping("/pwd/init")
    public void initPwd(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long id = this.getPostData(request, "id", -1);
        service.initPwd(id);
        ResponseResult.create()
                .put("message", "密码初始化成功")
                .print(response);
    }

    /**
     * 数据删除 -- 支持批量删除
     * @param request
     * @param response
     * @throws Exception
     */
    @PostMapping("/remove")
    public void delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 获取要删除的id集合
        JSONArray idsArray = this.getPostData(request, "ids", new JSONArray());
        List<Long> ids = idsArray.toJavaList(Long.class);
        int deleteNum = service.deleteAll(ids);
        ResponseResult.create()
                .put("num", deleteNum)
                .put("message", "删除成功")
                .print(response);
    }

    @Autowired
    private AdminService service;
}
