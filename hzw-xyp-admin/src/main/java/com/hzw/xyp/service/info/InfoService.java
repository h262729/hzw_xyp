package com.hzw.xyp.service.info;

import com.alibaba.fastjson.JSONObject;
import com.hzw.base.dao.QueryResultData;
import com.hzw.base.dao.SqlQuery;
import com.hzw.base.exception.BusinessException;
import com.hzw.base.tools.JSONTools;
import com.hzw.xyp.beans.info.Info;
import com.hzw.xyp.dao.info.InfoDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 资讯管理 -- 资讯文章管理
 */
@Service
@Transactional(readOnly = true)
public class InfoService {
    private Logger logger = LoggerFactory.getLogger(InfoService.class);

    /**
     * 列表查询
     * @param filter    查询条件
     * @param pageNum   第n页
     * @param pageSize  一页n条
     */
    public QueryResultData query(JSONObject filter, int pageNum, int pageSize){
        SqlQuery query = SqlQuery.create(filter)
                .select("t.*")
                .from("t_info t")
                .where("t.id = :id")
                .wheres("t", "title$like", "create_is_admin")
                .where("t.status != -1")
                .orderBy("t.id desc");
        QueryResultData resultDto = query.getResultList(dao, pageNum, pageSize);
        return resultDto;
    }

    /**
     * 获取实体数据
     * @param id
     * @return
     */
    public JSONObject get(long id){
        return null;
    }

    /**
     * 保存
     * @param postData
     */
    @Transactional
    public JSONObject save(JSONObject postData) throws Exception {
        Long id = postData.getLongValue("id");
        // 验证
        JSONTools.validate(postData, "name", "标签名称不能为空！");
        // 取值
        Info bean = id > 0? dao.get(id) : new Info();
        return null;
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
        Info bean = dao.get(id);
        if(bean == null){
            throw new BusinessException("获取不到相关数据！");
        }
        bean.setStatus(-1);
        dao.saveAndUpdate(bean);
        return 1;
    }

    @Autowired
    private InfoDao dao;
}
