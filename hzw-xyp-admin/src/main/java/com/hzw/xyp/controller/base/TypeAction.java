package com.hzw.xyp.controller.base;

import com.alibaba.fastjson.JSONObject;
import com.hzw.base.controller.BaseAction;
import com.hzw.base.controller.ResponseResult;
import com.hzw.base.dto.TreeDto;
import com.hzw.xyp.service.base.TypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * api接口 -- 基础管理 -- 分类管理
 * 这里以后考虑使用redis缓存
 */
@RestController
@RequestMapping(value = "/api/xyp-admin/base/type")
public class TypeAction extends BaseAction {
    private static final Logger logger = LoggerFactory.getLogger(TypeAction.class);

    /**
     * 获取分类树状结构数据
     */
    @GetMapping(value = "/trees")
    public void getTrees(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<TreeDto> trees = service.getTrees();
        ResponseResult.create().put("trees", trees).print(response);
    }

    /**
     * 保存
     */
    @PostMapping
    public void save(HttpServletRequest request, HttpServletResponse response) throws Exception {
        JSONObject postData = this.getPostData(request);
        TreeDto bean = service.save(postData);
        ResponseResult.create(bean).print(response);
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id:[\\d]+}")
    public void delete(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") Long id) throws Exception {
        service.delete(id);
        ResponseResult.create().put("msg", "删除成功！").print(response);
    }

    /**
     * 两节点之间交换排序
     */
    @PostMapping("/seq/exchange")
    public void exchangeSeq(HttpServletRequest request, HttpServletResponse response) throws Exception {
        JSONObject postData = this.getPostData(request);
        service.exchangeSeq(postData.getLongValue("id"), postData.getLongValue("id2"));
        ResponseResult.create().put("msg", "节点排序交换成功！").print(response);
    }


    @Autowired
    private TypeService service;

}
