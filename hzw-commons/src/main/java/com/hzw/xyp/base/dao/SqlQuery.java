package com.hzw.xyp.base.dao;

import com.alibaba.fastjson.JSONObject;
import com.hzw.xyp.base.exception.BusinessException;
import com.hzw.xyp.base.exception.ResponseCodeEnum;
import com.hzw.xyp.base.jpa.BaseRepository;
import com.hzw.xyp.base.tools.JSONTools;
import com.hzw.xyp.base.tools.StringTools;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * sql查询封装类
 */
public class SqlQuery {

    private String select;
    private String from;
    private String where;
    private String orderBy;
    private String groupBy;
    private static Map<String, Object> paramMap = new HashMap<String, Object>();   //条件参数

    public static Map<String, String> whereTabMap = new HashMap<>();
    static {
        whereTabMap.put("eq", " = :txt");
        whereTabMap.put("neq", "!= :txt");
        whereTabMap.put("like", " LIKE CONCAT('%',:txt,'%')");
        whereTabMap.put("rightLike", " LIKE CONCAT('%',:txt)");
        whereTabMap.put("leftLike", " LIKE CONCAT(:txt,'%')");
        whereTabMap.put("notLike", " NOT LIKE CONCAT('%',:txt,'%')");
        whereTabMap.put("notRightLike", " NOT LIKE CONCAT('%',:txt)");
        whereTabMap.put("notLeftLike", " NOT LIKE CONCAT(:txt,'%')");
        whereTabMap.put("in", " IN :txt");
        whereTabMap.put("notIn", " NOT IN :txt");
    }

    private SqlQuery(){

    }

    public static SqlQuery create(){
        return new SqlQuery();
    }

    public static SqlQuery create(Map<String, Object> paramMap){
        SqlQuery instance = new SqlQuery();
        instance.paramMap = paramMap;
        return instance;
    }

    public static SqlQuery create(JSONObject paramJson){
        SqlQuery instance = new SqlQuery();
        instance.paramMap = JSONTools.jsonObjectToMap(paramJson);
        return instance;
    }

    /**
     * 设置条件参数
     */
    public SqlQuery setConditions(Map<String, Object> paramMap){
        this.paramMap = paramMap;
        return this;
    }

    /**
     * 设置条件参数
     */
    public SqlQuery setConditions(JSONObject paramJson){
        this.paramMap = JSONTools.jsonObjectToMap(paramJson);
        return this;
    }

    /**
     * 分页查询
     * @param dao
     * @param sql 查询语句
     * @param pageNum   第n页
     * @param pageSize  一页多少条
     * @return
     */
    public QueryResultData getResultList(BaseRepository<?,?> dao, String sql, int pageNum, int pageSize){
        List<Map<String, Object>> resultList = dao.queryAsSql(sql, pageNum, pageSize);
        long totalCount = dao.countAsSql(sql);
        QueryResultData resultDto = new QueryResultData(resultList, pageNum, pageSize, totalCount);
        return resultDto;
    }

    /**
     * 分页查询
     * @param dao
     * @param sql 查询语句
     * @param pageNum   第n页
     * @param pageSize  一页多少条
     * @param paramMap  条件参数
     * @return
     */
    public QueryResultData getResultList(BaseRepository<?,?> dao, String sql, int pageNum, int pageSize, Map paramMap){
        List<Map<String, Object>> resultList = dao.queryAsSql(sql, pageNum, pageSize, paramMap);
        long totalCount = dao.countAsSql(sql, paramMap);
        QueryResultData resultDto = new QueryResultData(resultList, pageNum, pageSize, totalCount);
        return resultDto;
    }

    /**
     * 分页查询
     * @param dao
     * @param sql 查询语句
     * @param pageNum   第n页
     * @param pageSize  一页多少条
     * @param paramJson  条件参数
     * @return
     */
    public QueryResultData getResultList(BaseRepository<?,?> dao, String sql, int pageNum, int pageSize, JSONObject paramJson){
        Map<String, Object> paramMap = JSONTools.jsonObjectToMap(paramJson);
        return this.getResultList(dao, sql, pageNum, pageSize, paramMap);
    }

    /**
     * 分页查询
     * @param dao
     * @param pageNum   第n页
     * @param pageSize  一页多少条
     * @return
     */
    public QueryResultData getResultList(BaseRepository<?,?> dao, int pageNum, int pageSize){
        String sql = this.getSql();
        if(StringTools.isEmpty(sql)){
            return null;
        }
        return this.getResultList(dao, sql, pageNum, pageSize, this.paramMap);
    }

    /**
     * sql语句组合 --select语句
     * @param selectSql
     * @return
     */
    public SqlQuery select(String selectSql){
        if(StringTools.isEmpty(selectSql)){
            return this;
        }
        selectSql = selectSql.trim();   // 去空
        if(StringTools.isEmpty(this.select)){
            this.select = selectSql;
        }else{
            this.select += " " + selectSql;
        }
        return this;
    }

    /**
     * sql语句组合 --from语句
     * @param fromSql
     * @return
     */
    public SqlQuery from(String fromSql){
        if(StringTools.isEmpty(fromSql)){
            return this;
        }
        fromSql = fromSql.trim();   // 去空
        if(StringTools.isEmpty(this.from)){
            this.from = fromSql;
        }else{
            this.from += " " +  fromSql;
        }
        return this;
    }

    /**
     * sql语句组合 --where语句 默认and连接
     * @param whereSql
     * @param linkTab   连接字符 AND或OR
     * @param isFilter  是否条件参数过滤
     * @return
     */
    public SqlQuery where(String whereSql, LinkTab linkTab, boolean isFilter){
        if(StringTools.isEmpty(whereSql)){
            return this;
        }
        whereSql = whereSql.trim();   // 去空格
        // 没有相关的条件参数数据的话，就不进行sql拼接
        if(isFilter && whereSql.indexOf(":") != -1){
            String param = whereSql.substring(whereSql.lastIndexOf(":") + 1);
            if(StringTools.isNotEmpty(param) && !paramMap.containsKey(param)){
                return this;
            }
        }
        if(StringTools.isEmpty(this.where)){
            this.where = whereSql;
        }else{
            if(linkTab == LinkTab.OR){
                this.where += " OR " +  whereSql;
            }else{
                this.where += " AND " +  whereSql;
            }
        }
        return this;
    }

    /**
     * sql语句组合 --where语句 默认and连接 + 条件参数过滤
     * @param whereSql
     * @param linkTab
     * @return
     */
    public SqlQuery where(String whereSql, LinkTab linkTab){
        this.where(whereSql, linkTab, true);
        return this;
    }

    /**
     * sql语句组合 --where语句 --and连接
     */
    public SqlQuery where(String whereSql){
        this.where(whereSql, LinkTab.AND);
        return this;
    }

    /**
     * sql语句组合 --where语句 --or连接
     */
    public SqlQuery whereOr(String whereSql){
        this.where(whereSql, LinkTab.OR);
        return this;
    }

    /**
     * sql语句组合 --order by语句
     * @param orderBySql
     * @return
     */
    public SqlQuery orderBy(String orderBySql){
        if(StringTools.isEmpty(orderBySql)){
            return this;
        }
        orderBySql = orderBySql.trim();   // 去空
        if(StringTools.isEmpty(this.orderBy)){
            this.orderBy = orderBySql;
        }else{
            this.orderBy += " , " +  orderBySql;
        }
        return this;
    }

    /**
     * sql语句组合 --group by语句
     * @param groupBySql
     * @return
     */
    public SqlQuery groupBy(String groupBySql){
        if(StringTools.isEmpty(groupBySql)){
            return this;
        }
        groupBySql = groupBySql.trim();   // 去空
        if(StringTools.isEmpty(this.groupBy)){
            this.groupBy = groupBySql;
        }else{
            this.groupBy += " , " +  groupBySql;
        }
        return this;
    }

    /**
     * sql语句的最终合成
     * @return
     */
    public String getSql(){
        StringBuilder sqlBuilder = new StringBuilder();
        String sql = "";
        // select语句
        if(StringTools.isEmpty(this.select)){
            sql = "SELECT *";
        }else{
            sql = "SELECT " + select.trim();
        }

        // from语句
        if(StringTools.isEmpty(this.from)){
            throw new BusinessException(ResponseCodeEnum.SQL_QUERY_FAIL);
        }else{
            sql += " FROM " + this.from.trim();
        }

        // where语句
        if(!StringTools.isEmpty(this.where)){
            sql += " WHERE " + this.where;
        }

        // groupBy语句
        if(!StringTools.isEmpty(this.groupBy)){
            sql += " GROUP BY " + this.groupBy;
        }

        // orderBy语句
        if(!StringTools.isEmpty(this.orderBy)){
            sql += " ORDER BY " + this.orderBy;
        }

        System.out.println("SQL组装结果：" + sql);
        return sql;
    }

    /**
     * sql语句组合扩展 --where语句扩展 --默认and连接
     * @param as    表标记
     * @param params    where语句参数，格式：xxx$xx 默认不带$符号的相等于 name$eq
     *                  例如：id, name$eq, nickName$like
     *                  带有别名的格式： id$in$ids  第一个参数是表字段，第二个参数是连接字符，第三个参数是别名字段，用于取出条件参数中的数据
     * @return
     */
    public SqlQuery wheres(String as, LinkTab linkTab, String... params){
        for(String param : params){
            String[] array = param.split("\\$");
            if(StringTools.isEmpty(array[0])){
                continue;
            }
            String humpField = array[0];    // 字段名 --驼峰式
            String aliasField = humpField;  // 字段参数别名，主要用于in这些语句
            if(array.length == 3){
                aliasField = array[2];
            }
            // 如果没有相关的条件参数的话，就不进行相关的where的SQL语句拼接
            if(!paramMap.containsKey(aliasField)){
                continue;
            }

            String underlineField = StringTools.humpCaseToUnderline(humpField);    // 字段名 --下划线式
            String tab = "eq";    // 等式符号 -- 默认eq
            if(array.length >= 2){
                tab = array[1];
            }
            if(!whereTabMap.containsKey(tab)){
                continue;
            }
            String whereSql;
            if(StringTools.isEmpty(as)){
                whereSql = underlineField + " " + whereTabMap.get(tab).replaceFirst("txt", aliasField);
            }else{
                whereSql = as + "." + underlineField + " " + whereTabMap.get(tab).replaceFirst("txt", aliasField);
            }
            this.where(whereSql, linkTab, false);
        }
        return this;
    }

    /**
     * sql语句组合扩展 --where语句扩展 --and连接
     */
    public SqlQuery wheres(String as, String... params){
        this.wheres(as, LinkTab.AND, params);
        return this;
    }

    /**
     * sql语句组合扩展 --where语句扩展 --or连接
     */
    public SqlQuery whereOrs(String as, String... params){
        this.wheres(as, LinkTab.OR, params);
        return this;
    }

    /**
     * sql语句count查询 --获取count
     */
    public int getCount(BaseRepository<?,?> dao){
        String sql = this.getSql();
        return dao.countAsSql(sql);
    }

    /**
     * sql语句count查询 --获取count --带条件参数的
     */
    public int getCount(BaseRepository<?,?> dao, Map paramMap){
        String sql = this.getSql();
        return dao.countAsSql(sql, paramMap);
    }

    /**
     * where语句连接字符枚举
     */
    enum LinkTab{
        AND,
        OR,
    }
}
