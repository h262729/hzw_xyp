package com.hzw.xyp.base.dao;


import com.alibaba.fastjson.JSONArray;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 数据结构 -- 分页查询结果
 */
public class QueryResultData implements Serializable {

    private int pageSize;   // 分页大小
    private int pageNum;    // 第n页
    private long totalCount; // 结果总数
    private int pageCount;  //  分页数量
    private List<Map<String, Object>> rows = new ArrayList();  // 查询结果
    private JSONArray arrays = new JSONArray(); // 查询结果 -- jsonArray格式

    /**
     * 构造函数 -- 查询出所有的结果
     * @param rows
     */
    public QueryResultData(List<Map<String, Object>> rows){
        this(rows, 1, rows.size(), rows.size());
    }

    /**
     * 构造函数 -- 分页查询结果
     * @param rows  查询结果
     * @param pageNum   第n页
     * @param pageSize  分页大小
     * @param totalCount    总数
     */
    public QueryResultData(List<Map<String, Object>> rows, int pageNum, int pageSize, long totalCount){
        if(rows != null){
            this.rows.addAll(rows);
        }
        this.pageSize = pageSize;
        this.pageNum = pageNum;
        this.totalCount = totalCount;
        this.pageCount = pageSize == 0? 0 : (int)Math.ceil(totalCount * 1.0 / pageSize);
    }

    /**
     * 构造函数 -- 分页查询结果 JSONArray格式
     * @param arrays  查询结果  JSONArray格式
     * @param pageNum   第n页
     * @param pageSize  分页大小
     * @param totalCount    总数
     */
    public QueryResultData(JSONArray arrays, int pageNum, int pageSize, long totalCount){
        this.arrays.addAll(arrays);
        this.pageSize = pageSize;
        this.pageNum = pageNum;
        this.totalCount = totalCount;
        this.pageCount = pageSize == 0? 0 : (int)Math.ceil(totalCount * 1.0 / pageSize);
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public List<Map<String, Object>> getRows() {
        return rows;
    }

    public JSONArray getArrays() {
        return arrays;
    }

    public int getPageCount(){
        return pageCount;
    }
}
