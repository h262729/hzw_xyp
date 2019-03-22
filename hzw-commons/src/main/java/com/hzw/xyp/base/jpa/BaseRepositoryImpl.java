package com.hzw.xyp.base.jpa;

import org.hibernate.Session;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 接口实现 - 自定义jap接口
 */
public class BaseRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements BaseRepository<T,ID> {

    private final EntityManager entityManager;

    /**
     * 父类没有无参构造方法，这里手动构造父类
     * @param domainClass
     * @param entityManager
     */
    public BaseRepositoryImpl(Class<T> domainClass, EntityManager entityManager) {
        super(domainClass, entityManager);
        this.entityManager = entityManager;
    }

    /**
     * 获取session
     */
    @Override
    public Session getSession() {
        return this.entityManager.unwrap(Session.class);
    }

    /**
     * 获取entityManager
     */
    @Override
    public EntityManager getEntityManager() {
        return this.entityManager;
    }

    /**
     * 查询所有 -- 根据原生sql语句
     * @param sql   查询语句
     */
    @Override
    public List<Map<String, Object>> queryAsSql(String sql) {
        List<Map<String, Object>> result = this.getSession().createNativeQuery(sql)
                .setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE)
                .list();
        return result;
    }

    /**
     * 列表查询 -- 根据原生sql语句
     * @param sql   查询语句
     * @param pageNum   第n页
     * @param pageSize  一页多少条
     */
    @Override
    public List<Map<String, Object>> queryAsSql(String sql, int pageNum, int pageSize) {
        List<Map<String, Object>> result = this.getSession().createNativeQuery(sql)
                .setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE)
                .setFirstResult((pageNum - 1) * pageSize)
                .setMaxResults(pageSize)
                .list();
        return result;
    }

    /**
     * 列表查询 -- 根据原生sql语句
     * @param sql   查询语句
     * @param pageNum   第n页
     * @param pageSize  一页多少条
     * @param params  查询条件参数
     */
    @Override
    public List<Map<String, Object>> queryAsSql(String sql, int pageNum, int pageSize, Map params) {
        List<Map<String, Object>> result = this.getSession().createNativeQuery(sql)
                .setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE)
                .setFirstResult((pageNum - 1) * pageSize)
                .setMaxResults(pageSize)
                .setProperties(params)
                .list();
        return result;
    }

    /**
     * count语句查询 -- 根据原生sql语句
     * @param sql   查询语句
     * @return
     */
    @Override
    public int countAsSql(String sql) {
        // 重新生成count查询语句
        String countSql = this.getCountSql(sql);
        Object count = this.getSession()
                .createNativeQuery(countSql)
                .addScalar("ct", StandardBasicTypes.INTEGER)
                .uniqueResult();
        return count == null? 0 : Integer.valueOf(count.toString());
    }

    /**
     * count语句查询 -- 根据原生sql语句
     * @param sql   查询语句
     * @param params    查询条件参数
     * @return
     */
    @Override
    public int countAsSql(String sql, Map params) {
        // 重新生成count查询语句
        String countSql = this.getCountSql(sql);
        Object count = this.getSession()
                .createNativeQuery(countSql)
                .addScalar("ct", StandardBasicTypes.INTEGER)
                .setProperties(params)
                .uniqueResult();
        return count == null? 0 : Integer.valueOf(count.toString());
    }

    private String getCountSql(String sql){
        String countSql = "SELECT COUNT(1) AS ct ";
        if(sql.indexOf("limit") != -1){
            countSql += sql.substring(sql.indexOf("FROM"), sql.indexOf("limit"));
        }else if(sql.indexOf("LIMIT") != -1){
            countSql += sql.substring(sql.indexOf("FROM"), sql.indexOf("LIMIT"));
        }else{
            countSql += sql.substring(sql.indexOf("FROM"));
        }
        return countSql;
    }
}
