package com.hzw.base.jpa;

import com.hzw.base.dao.SqlQuery;
import com.hzw.base.tools.BeanTools;
import com.hzw.base.tools.StringTools;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.hibernate.type.StandardBasicTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.*;

/**
 * 接口实现 - 自定义jap接口
 */
public class BaseRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements BaseRepository<T,ID> {
    private static final Logger logger = LoggerFactory.getLogger(BaseRepositoryImpl.class);

    private final Class<T> domainClass;

    private final EntityManager entityManager;

    /**
     * 父类没有无参构造方法，这里手动构造父类
     * @param domainClass
     * @param entityManager
     */
    public BaseRepositoryImpl(Class<T> domainClass, EntityManager entityManager) {
        super(domainClass, entityManager);
        this.entityManager = entityManager;
        this.domainClass = domainClass;
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

    /**
     * 获取实体类数据
     */
    @Override
    public T getByProperties(Object... keyAndValues) {
        List<T> result = this.getBeans(keyAndValues, 1);
        return result == null || result.size() == 0? null : result.get(0);
    }

    /**
     * 获取所有符合条件的实体类数据
     */
    @Override
    public List<T> getAllByProperties(Object... keyAndValues) {
        List<T> result = this.getBeans(keyAndValues, null);
        return result;
    }

    /**
     * 判断数据是否已存在
     * @param keyAndValues  判断条件
     * @return
     */
    @Override
    public boolean exist(Object... keyAndValues) {
        List<T> beans = this.getBeans(keyAndValues, 1);
        if(beans == null || beans.size() == 0){
            return false;
        }
        return true;
    }

    /**
     * 判断数据是否已存在，排除当前id之外
     * @param id
     * @param keyAndValues
     * @return
     */
    @Override
    public boolean existExcludeId(ID id, Object... keyAndValues) {
        Object[] newKeyAndValues = Arrays.copyOf(keyAndValues, keyAndValues.length + 2);
        newKeyAndValues[keyAndValues.length] = "id$neq";
        newKeyAndValues[keyAndValues.length + 1] = id;
        return this.exist(newKeyAndValues);
    }

    /**
     * 自定义获取实体
     * 这里不使用dao.getOne(id), 不然老是莫名其妙报java.lang.NullPointerException异常，断点检查了半天，还是没搞明白，气死我了
     * @param id
     * @return
     */
    @Override
    public T get(ID id) {
        if(id == null){
            return null;
        }
        return this.getSession().get(domainClass, id);
    }

    /**
     * 自定义保存方法，替换掉jpa自带的save()方法
     * @param bean
     */
    @Override
    public void saveAndUpdate(T bean) throws Exception {
        if(bean == null){
            return;
        }

        // 获取实体类id
        ID id = null;
        String idStr = BeanTools.getProperty(bean, "id");
        if(!StringTools.isEmpty(idStr)){
            id = (ID) idStr;
        }

        // 更新 updateTime 和 createTime
        Date now = new Date();
        if(id == null){ // 新增操作
            BeanTools.setProperty(bean, "updateTime", now);
            BeanTools.setProperty(bean, "createTime", now);
        }else{ // 保存操作
            BeanTools.setProperty(bean, "updateTime", now);
            if (StringTools.isEmpty(BeanTools.getProperty(bean, "createTime"))) {
                BeanTools.setProperty(bean, "createTime", now);
            }
        }
        // 执行保存操作
        this.getSession().saveOrUpdate(bean);
    }

    /**
     * 批量更新数据
     * @param name  目标字段
     * @param value 更新值
     * @param keyAndValues  条件参数
     * @return 更新影响条数
     */
    @Override
    public int updateAllByProperties(String name, Object value, Object... keyAndValues) {
        // 更新hql语句
        String hql = "UPDATE " + domainClass.getSimpleName() + " SET " + name + " = :" + name;
        hql += this.createWhereAndOrder(keyAndValues);  // 获取where条件语句
        // 参数
        Map<String, Object> params = this.getParams(keyAndValues);

        logger.info("批量更新hql语句 > " + hql);
        // 批量更新
        int updateNum = this.getSession().createQuery(hql)
                .setParameter(name, value)
                .setProperties(params)
                .executeUpdate();
        return updateNum;
    }

    /**
     * 更新单条数据
     * @param id  目标id
     * @param keyAndValues  更新参数
     * @return
     */
    @Override
    public int update(Long id, Object... keyAndValues) {
        // 生成set语句
        if(keyAndValues == null || keyAndValues.length%2 != 0){
            return 0;
        }
        StringBuilder setHql = new StringBuilder();
        for(int i = 0; i < keyAndValues.length; i+=2){
            String key = (String) keyAndValues[i];
            if(StringTools.isEmpty(setHql.toString())){
                setHql.append(" " + StringTools.humpCaseToUnderline(key) + " = :" + key);
            }else{
                setHql.append(" , " + StringTools.humpCaseToUnderline(key) + " = :" + key);
            }
        }

        // 更新hql语句
        String hql = "UPDATE " + domainClass.getSimpleName() + " t SET " + setHql.toString() + " WHERE id = :id";
        // 参数
        Map<String, Object> params = this.getParams(keyAndValues);
        logger.info("单条更新hql语句 > " + hql);
        // 批量更新
        int updateNum = this.getSession().createQuery(hql)
                .setParameter("id", id)
                .setProperties(params)
                .setMaxResults(1)
                .executeUpdate();
        return updateNum;
    }

    /**
     * 查询出所有符合条件的数据 -- 辅助方法
     * @param keyAndValues  条件参数
     *                      格式：奇数位的为 key, 偶数位的为 value
     *                      key的格式默认判断符tab为 =, 其余的为 key$tab, tab具体请参考默认定义的whereTabMap
     * @param maxCount  最大查询数量
     * @return
     */
    private List<T> getBeans(Object[] keyAndValues, Integer maxCount){
        if(keyAndValues == null || keyAndValues.length%2 != 0){
            return null;
        }
        String hql = " FROM " + domainClass.getSimpleName(); // hql查询语句
        hql += this.createWhereAndOrder(keyAndValues); // 条件语句拼接
        Map<String, Object> params = this.getParams(keyAndValues); // 获取条件参数

        logger.info("查询hql语句 > " + hql);
        Query query = this.getSession().createQuery(hql).setProperties(params);
        if(maxCount != null && maxCount > -1){
            query.setMaxResults(maxCount);
        }
        List<T> result = query.getResultList();
        return result;
    }

    /**
     * 生成where语句
     * @param keyAndValues xxx$order 为order by语句，第二个值参数必须为 desc或asc
     *                     其余的where语句连接符 请参照SqlQuery.whereTabMap
     * @return
     */
    private String createWhereAndOrder(Object[] keyAndValues){
        if(keyAndValues == null || keyAndValues.length%2 != 0){
            return "";
        }
        StringBuilder where = new StringBuilder();  // where
        StringBuilder order = new StringBuilder();  // order by 语句
        // 根据条件参数,进行hql语句拼接
        for(int i = 0; i < keyAndValues.length; i+=2){
            String keyAndTabStr = (String) keyAndValues[i];
            if(StringTools.isEmpty(keyAndTabStr)){
                continue;
            }
            String[] keyAndTabs = keyAndTabStr.split("\\$");
            String key = keyAndTabs[0]; // 字段名
            String tab = keyAndTabs.length == 2? keyAndTabs[1] : null;  // 判断符，默认 =

            if("order".equalsIgnoreCase(tab)){ // 如果是order排序语句的话
                // 判断是desc,还是asc
                String value = (String)keyAndValues[i+1];
                if("desc".equalsIgnoreCase(value)){
                    order.append(" " + StringTools.humpCaseToUnderline(key) + " DESC");
                }else if("asc".equalsIgnoreCase(value)){
                    order.append(" " + StringTools.humpCaseToUnderline(key) + " ASC");
                }
                continue;
            }

            if(StringTools.isEmpty(where.toString())){
                where.append(" WHERE " + StringTools.humpCaseToUnderline(key));
            }else{
                where.append(" AND " + StringTools.humpCaseToUnderline(key));
            }

            if(tab != null && SqlQuery.whereTabMap.containsKey(tab)){
                where.append(SqlQuery.whereTabMap.get(tab).replaceFirst("txt", key));
            }else{
                where.append(" = :" + key);
            }
        }

        String whereAndOrder = where.toString();
        if(StringTools.isNotEmpty(order.toString())){
            whereAndOrder += " ORDER BY " + order.toString();
        }
        return whereAndOrder;
    }

    /**
     * 获取参数
     * @param keyAndValues 排除xxx$order的参数
     * @return
     */
    private Map<String, Object> getParams(Object[] keyAndValues){
        if(keyAndValues == null || keyAndValues.length%2 != 0){
            return new HashMap();
        }
        Map<String, Object> params = new HashMap();
        for(int i = 0; i < keyAndValues.length; i+=2){
            String keyAndTabStr = (String) keyAndValues[i];
            if(StringTools.isEmpty(keyAndTabStr)){
                continue;
            }
            String[] keyAndTabs = keyAndTabStr.split("\\$");
            String key = keyAndTabs[0]; // 字段名
            String tab = keyAndTabs.length == 2? keyAndTabs[1] : null;  // 判断符，默认 =
            if("order".equalsIgnoreCase(tab)){ // 如果是order排序语句的话
                continue;
            }

            params.put(key, keyAndValues[i+1]); // 条件参数
        }
        return params;
    }

    /**
     * 获取查询数量的sql语句
     * @param sql
     * @return
     */
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
