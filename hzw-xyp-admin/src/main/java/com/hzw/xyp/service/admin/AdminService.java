package com.hzw.xyp.service.admin;

import com.alibaba.fastjson.JSONObject;
import com.hzw.xyp.dao.admin.AdminDao;
import com.hzw.xyp.base.dao.QueryResultData;
import com.hzw.xyp.base.dao.SqlQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 服务层 -- 管理员
 */
@Service
public class AdminService {

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
                .wheres("t", "name$like", "id$in$ids");
        QueryResultData resultDto = query.getResultList(dao, pageNum, pageSize);
        return resultDto;
    }

    /**
     * 新增/修改保存
     * @param params
     */
    public void save(JSONObject params){

    }

    /**
     * 加载获取
     * @param id
     */
    public void load(int id){

    }

    /**
     * 无效
     * @param id
     */
    public void setInvalid(int id){

    }

    /**
     * 删除
     * @param id
     */
    public void delete(int id){

    }

    @Autowired
    private AdminDao dao;
}
