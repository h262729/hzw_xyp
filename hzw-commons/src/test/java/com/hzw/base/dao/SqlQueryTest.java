package com.hzw.base.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SqlQueryTest {

    @Test
    public void sqlTest003(){
        String sql = "SELECT t.name FROM b_admin t WHERE  name LIKE '%:name' limit 10,23";
        String countSql = "SELECT COUNT(1) AS ct ";
        if(sql.indexOf("limit") != -1){
            countSql += sql.substring(sql.indexOf("FROM"), sql.indexOf("limit"));
        }else if(sql.indexOf("LIMIT") != -1){
            countSql += sql.substring(sql.indexOf("FROM"), sql.indexOf("LIMIT"));
        }else{
            countSql += sql.substring(sql.indexOf("FROM"));
        }
        System.out.println("count结果：" + countSql);
    }

    @Test
    public void sqlTest002(){
        SqlQuery query = SqlQuery.create()
                .select("t.id")
                .from("b_admin t")
                .wheres("t", SqlQuery.LinkTab.OR, "name", "nickName$like", "id$notIn");
        String sql = query.getSql();
        System.out.println("组合结果：" + sql);
    }

    @Test
    public void sqlTest001(){
        SqlQuery query = SqlQuery.create()
                .select("t.id")
                .from("b_admin t")
                .where("t.id = 1")
                .where("t.name LIKE '%黄' OR t.name = :name ")
                .groupBy("t.id")
                .orderBy("t.name ASC");
        for(int i = 0; i < 1000; i++){
            query.wheres("t", "id");
            query.wheres("t", "name");
        }
        String sql = query.getSql();
        System.out.println("组合结果：" + sql);
    }

    private long startTime = 0L;
    private long endTime = 0L;
    @Before
    public void before(){
        System.out.println("## 开始测试");
        startTime = System.currentTimeMillis();
    }
    @After
    public void after(){
        endTime = System.currentTimeMillis();
        System.out.println("## 测试结束");
        System.out.println("## 测试用时：" + (endTime - startTime));

    }
}
