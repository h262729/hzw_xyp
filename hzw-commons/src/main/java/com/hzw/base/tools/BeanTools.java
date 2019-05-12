package com.hzw.base.tools;


import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * 工具类 --- 操作实体类
 */
public class BeanTools {

    /**
     * 获取实体类字段数据
     */
    public static String getProperty(Object bean, String fieldName){
        try {
            return BeanUtils.getProperty(bean, fieldName);
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 设置实体类某字段数据
     */
    public static boolean setProperty(Object bean, String name, Object value) throws Exception {
        for(Field field : bean.getClass().getDeclaredFields()){
            String fieldName = field.getName();
            if(fieldName.equals(name)){
                field.setAccessible(true);
                field.set(bean, value);
                return true;//表示匹配到了
            }
        }
        return false;//表示没有匹配到这个字段
    }

    /**
     * 判断实体类是否存在某字段
     */
    public static boolean existsField(Class<?> clazz, String fieldName){
        try {
            return clazz.getDeclaredField(fieldName) != null;
        } catch (NoSuchFieldException e) {
        }
        return false;
    }

    /**
     * 填充替换实体类数据
     * @param bean
     * @param properties
     * @throws Exception
     */
    public static void populate(Object bean, Map<String, Object> properties) throws Exception {
        BeanUtilsBean beanUtilBean = new BeanUtilsBean();
        beanUtilBean.populate(bean, properties);
    }
}
