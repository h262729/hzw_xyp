package com.hzw.base.dto;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * 树状数据结构
 */
public class TreeDto {
    private long id;
    private String name;
    private long parentId = -1;
    private String parentName;
    private JSONObject beanData;
    private List<TreeDto> childs;


    public TreeDto(Long id, String name, Long parentId, String parentName, List<TreeDto> childs){
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.parentName = parentName;
        this.childs = childs;
    }

    public TreeDto(Long id, String name, Long parentId, String parentName, JSONObject beanData, List<TreeDto> childs){
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.parentName = parentName;
        this.beanData = beanData;
        this.childs = childs;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getParentId() {
        return parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public JSONObject getBeanData() {
        return beanData;
    }

    public List<TreeDto> getChilds() {
        return childs;
    }


}
