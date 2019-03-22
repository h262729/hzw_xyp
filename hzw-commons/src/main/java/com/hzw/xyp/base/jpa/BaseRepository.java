package com.hzw.xyp.base.jpa;

import org.hibernate.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 接口 - 自定义jap接口
 */
@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {

    /**
     * 获取session
     */
    Session getSession();

    /**
     * 获取entityManager
     */
    EntityManager getEntityManager();

    /**
     * 查询所有 -- 根据原生sql语句
     * @param sql   查询语句
     */
    List<Map<String, Object>> queryAsSql(String sql);

    /**
     * 列表分页查询 -- 根据原生sql语句
     * @param sql   查询语句
     * @param pageNum   第n页
     * @param pageSize  一页多少条
     */
    List<Map<String, Object>> queryAsSql(String sql, int pageNum, int pageSize);

    /**
     * 列表分页查询 -- 根据原生sql语句
     * @param sql   查询语句
     * @param pageNum   第n页
     * @param pageSize  一页多少条
     * @param params  查询条件参数
     */
    List<Map<String, Object>> queryAsSql(String sql, int pageNum, int pageSize, Map params);

    /**
     * count语句查询 -- 根据原生sql语句
     * @param sql   查询语句
     * @return
     */
    int countAsSql(String sql);

    /**
     * count语句查询 -- 根据原生sql语句
     * @param sql   查询语句
     * @param params    查询条件参数
     * @return
     */
    int countAsSql(String sql, Map params);
}
