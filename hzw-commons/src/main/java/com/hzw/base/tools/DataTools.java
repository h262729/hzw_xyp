package com.hzw.base.tools;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hzw.base.dto.TreeDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据结构工具类
 * 1、获取树状数据结构
 */
public class DataTools {

    /**
     * 获取树状数据结构
     * @param arrays 一次性查出的所有数据
     * @return
     */
    public static List<TreeDto> getTrees(JSONArray arrays){ //
        // 根据parentId 对所有数据进行归类
        Map<Long, JSONArray> sortMap = new HashMap<Long, JSONArray>();
        for(int i = 0; i < arrays.size(); i++){
            JSONObject beanJson = arrays.getJSONObject(i);
            Long id = beanJson.getLongValue("id");
            if(id == null || id <= 0){
                continue;
            }
            Long parentId = beanJson.getLongValue("parentId");
            parentId = (parentId == null || parentId <= 0)? -1 : parentId;

            JSONArray arrays2 = null;
            if(sortMap.containsKey(parentId)){
                arrays2 = sortMap.get(parentId);
                arrays2 = arrays2 == null? new JSONArray() : arrays2;
            }else{
                arrays2 = new JSONArray();
            }
            arrays2.add(beanJson);
            sortMap.put(parentId, arrays2);
        }
        List<TreeDto> treeDtoList = getTrees(sortMap, -1L, "");
        return treeDtoList;
    }

    /**
     * 获取树状数据结构
     * @param sortMap 已分类的数据集
     * @param parentId
     * @param parentName
     * @return
     */
    private static List<TreeDto> getTrees(Map<Long, JSONArray> sortMap, Long parentId, String parentName){
        JSONArray array = sortMap.get(parentId);
        if(parentId == null || array == null || array.size() <= 0){
            return new ArrayList<>();
        }
        List<TreeDto> treeDtoList = new ArrayList<TreeDto>();
        for(int i = 0; i < array.size(); i++){
            JSONObject beanJson = array.getJSONObject(i);
            Long beanId = beanJson.getLongValue("id");
            String beanName = beanJson.getString("name");
            List<TreeDto> childs = getTrees(sortMap, beanId, beanName);  // 递归获取子节点
            TreeDto dto = new TreeDto(beanId, beanName, parentId, parentName, beanJson, childs);
            treeDtoList.add(dto);
        }
        return treeDtoList;
    }
}
