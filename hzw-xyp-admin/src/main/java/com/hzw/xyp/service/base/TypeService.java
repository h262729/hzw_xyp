package com.hzw.xyp.service.base;

import com.alibaba.fastjson.JSONObject;
import com.hzw.base.dto.TreeDto;
import com.hzw.base.exception.BusinessException;
import com.hzw.base.tools.DataTools;
import com.hzw.base.tools.JSONTools;
import com.hzw.xyp.beans.base.Type;
import com.hzw.xyp.dao.base.TypeDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class TypeService {
    private static final Logger logger = LoggerFactory.getLogger(TypeService.class);

    /**
     * 获取树节点数据
     * @return
     */
    public List<TreeDto> getTrees(){
        // 先一次性将所有的有效数据拿出来
        List<Type> typeList = dao.getAllByProperties("status", 1, "seq$order", "asc");

        long startTime = System.currentTimeMillis();
        List<TreeDto> trees = DataTools.getTrees(JSONTools.toJSONArray(typeList));
        long endTime = System.currentTimeMillis();
        logger.info("树状结构生成所花费的时间：" + (endTime - startTime));
        return trees;
    }

    /**
     * 新增或保存
     * 注：保存的时候要过掉seq， seq排序只能在新增的时候和交换节点是发生修改，其余时候不给
     */
    @Transactional
    public TreeDto save(JSONObject postData) throws Exception {
        Long id = postData.getLongValue("id");
        // 验证
        JSONTools.validate(postData, "name", "分类名称不能为空！");
        // 获取实体
        Type bean = id > 0? dao.get(id) : new Type();
        if(bean == null){
            throw new BusinessException("请求数据有误！");
        }
        
        if(id <= 0){ // 新增操作
            Long parentId = postData.getLongValue("parentId");
            // 设置排序
            Type lastBean = dao.getByProperties("parentId", parentId, "status", 1, "seq$order", "desc");
            if(lastBean != null){
                // 获取到排序最后的一个
                int lastSeq = lastBean.getSeq();
                bean.setSeq(lastSeq + 1);
            }
        }

        JSONTools.populate(bean, postData, JSONTools.Filter.getInstance().blacks("id", "seq", "status", "updateTime", "createTime"));
        // 保存
        dao.saveAndUpdate(bean);

        TreeDto dto = new TreeDto(bean.getId(), bean.getName(), bean.getParentId(), postData.getString("parentName"), JSONTools.toJSONObject(bean), null);
        return dto;
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
        Type bean = dao.get(id);
        if(bean == null){
            throw new BusinessException("获取不到相关数据！");
        }
        bean.setStatus(-1);
        dao.saveAndUpdate(bean);
        return 1;
    }

    /**
     * 两节点之间交换排序
     */
    @Transactional
    public void exchangeSeq(Long id, Long id2){
        Type bean = dao.get(id);
        Type bean2 = dao.get(id2);
        if(bean == null || bean2 == null){
            throw new BusinessException("请求数据有误！");
        }
        dao.update(id, "seq", bean2.getSeq());
        dao.update(id2, "seq", bean.getSeq());
    }

    @Autowired
    private TypeDao dao;
}
