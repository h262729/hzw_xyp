package com.hzw.base.jpa;

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

    /**
     * 获取实体类数据
     */
    T getByProperties(Object... keyAndValues);

    /**
     * 获取所有符合条件的实体类数据
     */
    List<T> getAllByProperties(Object... keyAndValues);

    /**
     * 判断数据是否已存在
     * @param keyAndValues  判断条件
     * @return
     */
    boolean exist(Object... keyAndValues);

    /**
     * 判断数据是否已存在，排除当前id之外
     * @param id
     * @param keyAndValues
     * @return
     */
    boolean existExcludeId(ID id, Object... keyAndValues);

    /**
     * 自定义获取实体
     * @param id
     * @return
     */
    T get(ID id);

    /**
     * 自定义保存方法，替换掉jpa自带的save()方法
     * @param bean
     */
    void saveAndUpdate(T bean) throws Exception;

    /**
     * 批量更新数据
     * @param name  目标字段
     * @param value 更新值
     * @param keyAndValues  条件参数
     * @return
     */
    int updateAllByProperties(String name, Object value, Object... keyAndValues);

    /**
     * 更新单条数据
     * @param id  目标id
     * @param keyAndValues  更新参数
     * @return
     */
    int update(Long id, Object... keyAndValues);
}
