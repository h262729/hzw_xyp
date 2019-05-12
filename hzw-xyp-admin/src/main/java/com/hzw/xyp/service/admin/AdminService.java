package com.hzw.xyp.service.admin;

import com.alibaba.fastjson.JSONObject;
import com.hzw.base.constant.RegexManage;
import com.hzw.base.dao.QueryResultData;
import com.hzw.base.dao.SqlQuery;
import com.hzw.base.exception.BusinessException;
import com.hzw.base.tools.JSONTools;
import com.hzw.base.tools.OtherTools;
import com.hzw.base.tools.others.EncryptUtil;
import com.hzw.xyp.beans.admin.Admin;
import com.hzw.xyp.dao.admin.AdminDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 服务层 -- 管理员
 */
@Service
@Transactional(readOnly = true)
public class AdminService {
    private Logger logger = LoggerFactory.getLogger(AdminService.class);

    private final static String INIT_PWD = "123456";   // 默认初始化密码

    /**
     * 列表查询
     * @param filter    查询条件
     * @param pageNum   第n页
     * @param pageSize  一页n条
     */
    public QueryResultData query(JSONObject filter, int pageNum, int pageSize){
        SqlQuery query = SqlQuery.create(filter)
                .select("t.*")
                .from("b_admin t")
                .where("t.id = :id")
                .wheres("t", "name$like", "nickname$like", "id$in$ids")
                .where("t.status != -1")
                .orderBy("t.id desc");
        QueryResultData resultDto = query.getResultList(dao, pageNum, pageSize);
        return resultDto;
    }

    /**
     * 管理员登录
     */
    public Admin login(String name, String pwd) throws Exception {
        Admin bean = dao.getByProperties("name", name, "pwd", EncryptUtil.getMD5(pwd));
        if(bean == null){
            throw new BusinessException("登录失败！");
        }
        if(bean.getStatus() == -1){
            throw new BusinessException("该账号已被停用！");
        }
        return bean;
    }

    /**
     * 获取实体数据
     * @param id
     * @return
     */
    public JSONObject get(long id){
        Admin bean = dao.getOne(id);
        // 转JSONObect格式，顺便过滤掉敏感信息
        JSONObject beanJson = JSONTools.toJSONObject(bean, JSONTools.Filter.getInstance().blacks("pwd"));
        logger.info("获取实体：" + JSONObject.toJSONString(bean));
        return beanJson;
    }

    /**
     * 保存
     * @param postData
     */
    @Transactional
    public JSONObject save(JSONObject postData) throws Exception {
        long id = postData.getLongValue("id");
        // 数据校验
        JSONTools.validate(postData, "name", "用户名不能为空!", "email$email", "邮箱格式有误！");
        // 验证用户名格式
        OtherTools.regexValidate(postData.getString("name"), RegexManage.USER_NAME, "用户名格式有误！");

        // 判断用户名是否已存在
        if(dao.existExcludeId(id, "name", postData.getString("name"))){
            throw new BusinessException("该用户名已存在！");
        }

        // 获取数据
        Admin bean = id > 0? dao.get(id) : new Admin();
        if(bean == null){
            throw new BusinessException("数据请求有误！");
        }
        // 初始化密码
        if(id <= 0){    // 新增的时候初始化密码
            bean.setPwd(EncryptUtil.getMD5(this.INIT_PWD));
        }

        JSONTools.populate(bean, postData, JSONTools.Filter.getInstance().blacks("id", "pwd", "status", "updateTime", "createTime"));
        // 保存
        dao.saveAndUpdate(bean);

        JSONObject beanJson = JSONTools.toJSONObject(bean, JSONTools.Filter.getInstance().blacks("pwd"));
        return beanJson;
    }

    /**
     * 重新初始化密码
     * @param id
     */
    @Transactional
    public void initPwd(Long id) throws Exception {
        Admin bean = dao.get(id);
        if(bean == null){
            throw new BusinessException("数据请求有误！");
        }
        bean.setPwd(EncryptUtil.getMD5(this.INIT_PWD));
        dao.saveAndUpdate(bean);
    }

    /**
     * 批量删除
     * @param ids
     * @return 删除影响条数
     */
    @Transactional
    public int deleteAll(List<Long> ids) throws Exception {
        if(ids == null || ids.size() == 0){
            throw new BusinessException("数据请求有误！");
        }
        // 如果只有一条删除的话
        if(ids.size() == 1){
            return this.delete(ids.get(0));
        }
        // 批量删除更新
        int deleteNum = dao.updateAllByProperties("status", -1, "id$in", ids);
        return deleteNum;
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @Transactional
    public int delete(long id) throws Exception {
        if(id <= 0){
            throw new BusinessException("数据请求有误！");
        }
        Admin bean = dao.get(id);
        if(bean == null){
            throw new BusinessException("获取不到相关数据！");
        }
        bean.setStatus(-1);
        dao.saveAndUpdate(bean);
        return 1;
    }

    @Autowired
    private AdminDao dao;
}
