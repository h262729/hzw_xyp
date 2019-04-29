package com.hzw.xyp.base.tools;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.hzw.xyp.base.constant.RegexManage;
import com.hzw.xyp.base.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * JSON工具类
 */
public class JSONTools {
    private static Logger logger = LoggerFactory.getLogger(JSONTools.class);

    private static SerializeConfig serializeConfig = new SerializeConfig();
    private static final SerializerFeature[] features = {
            SerializerFeature.WriteMapNullValue, // 输出空置字段(是否输出值为null的字段,默认为false)
            //		SerializerFeature.WriteNullListAsEmpty, // list字段如果为null，输出为[]，而不是null
            //		SerializerFeature.WriteNullNumberAsZero, // 数值字段如果为null，输出为0，而不是null
            //		SerializerFeature.WriteNullBooleanAsFalse, // Boolean字段如果为null，输出为false，而不是null
            //		SerializerFeature.WriteNullStringAsEmpty, // 字符类型字段如果为null，输出为""，而不是null
            SerializerFeature.WriteDateUseDateFormat
    };
    static {
        serializeConfig.put(Date.class, new SimpleDateFormatSerializer(DateTools.format2));
    }


    public static JSON objToJson(Object obj){
        String jsonStr = null;
        if(obj instanceof String){  // 如果是字符串，则将其转换成对象
            jsonStr = String.valueOf(obj);
        }else{
            jsonStr = JSON.toJSONString(obj, serializeConfig, features);
        }
        Object parseObj = JSON.parse(jsonStr);
        if(parseObj instanceof JSONObject){
            return (JSONObject) parseObj;
        }else if(parseObj instanceof JSONArray){
            return (JSONArray) parseObj;
        }else{
            return null;
        }
    }

    /**
     * 转JSONObject
     * @param obj
     * @return
     */
    public static JSONObject toJSONObject(Object obj){
        JSON json = JSONTools.objToJson(obj);
        if(json instanceof JSONObject){
            return (JSONObject) json;
        }else{
            return new JSONObject();
        }
    }

    /**
     * 转JSONObject + Filter过滤
     * @param obj
     * @return
     */
    public static JSONObject toJSONObject(Object obj, Filter filter){
        JSONObject jsonObject = JSONTools.toJSONObject(obj);
        JSONTools.filterAsJSONObject(jsonObject, filter);
        return jsonObject;
    }

    /**
     * 转JSONArray
     * @param obj
     * @return
     */
    public static JSONArray toJSONArray(Object obj){
        JSON json = JSONTools.objToJson(obj);
        if(json instanceof JSONArray){
            return (JSONArray) json;
        }else{
            return new JSONArray();
        }
    }

    /**
     * 转JSONArray + Filter过滤
     * @param obj
     * @return
     */
    public static JSONArray toJSONArray(Object obj, Filter filter){
        JSONArray jsonArray = JSONTools.toJSONArray(obj);
        JSONTools.filterAsJSONArray(jsonArray, filter);
        return jsonArray;
    }

    /**
     * JSONObject 转 Map
     * @param params
     * @return
     */
    public static Map<String, Object> jsonObjectToMap(JSONObject params){
        Map<String, Object> paramsMap = params.getInnerMap();
        return paramsMap;
    }

    /**
     * 数据过滤 --JSON格式
     */
    public static void filter(JSON json, Filter filter){
        if(json instanceof JSONObject){
            filterAsJSONObject((JSONObject)json, filter);
        }else if(json instanceof JSONArray){
            filterAsJSONArray((JSONArray)json, filter);
        }
    }

    /**
     * 数据过滤 --Object格式
     * 这里如果非JSON的，需要接受转换后的json;
     */
    public static JSON filterAsObject(Object object, Filter filter){
        JSON json = objToJson(object);
        filter(json, filter);
        return json;
    }


    /**
     * 数据过滤 --JSONObject格式
     */
    public static void filterAsJSONObject(JSONObject jsonObject, Filter filter){
        if(jsonObject == null || jsonObject.isEmpty()){
            return;
        }

        List<String> whites = filter.getWhites();
        List<String> blacks = filter.getBlacks();
        if(whites.size() > 0){
            // 准备删除的参数
            List<String> readyRemoves = new ArrayList<String>();
            for(String key : jsonObject.keySet()){
                if(whites.contains(key)){
                    Object value = jsonObject.get(key);
                    if(value instanceof JSON){
                        filter((JSON)value, filter);
                    }
                }else{
                    readyRemoves.add(key);
                }
            }
            // 删除白名单外的数据
            for(String key : readyRemoves){
                jsonObject.remove(key);
            }
        }

        if(blacks.size() > 0){
            // 删除黑名单上的数据
            for(String key : blacks){
                jsonObject.remove(key);
            }
            for(String key : jsonObject.keySet()){
                Object value = jsonObject.get(key);
                if(value instanceof JSON){
                    filter((JSON) value, filter);
                }
            }
        }
    }

    /**
     * 数据过滤 --JSONArray格式
     */
    public static void filterAsJSONArray(JSONArray jsonArray, Filter filter){
        for(int i = 0; jsonArray != null && i < jsonArray.size(); i++){
            Object value = jsonArray.get(i);
            if(value instanceof JSON){
                filter((JSON)value, filter);
            }
        }
    }

    /**
     * 内部类 --过滤名单
     * 优先级：白名单 > 黑名单
     */
    public static class Filter{
        private List<String> whites = new ArrayList<String>();  // 白名单, 保留
        private List<String> blacks = new ArrayList<String>();  // 黑名单，去除

        private Filter(){
        }

        public static Filter getInstance(){
            return new Filter();
        }

        /**
         * 白名单
         */
        public Filter whites(String... values){
            for(int i = 0; values != null && i < values.length; i++){
                this.whites.add(values[i]);
            }
            return this;
        }

        /**
         * 白名单
         */
        public Filter whites(List<String> values){
            if(values != null){
                this.whites.addAll(values);
            }
            return this;
        }

        /**
         * 黑名单
         */
        public Filter blacks(String... values){
            for(int i = 0; values != null && i < values.length; i++){
                this.blacks.add(values[i]);
            }
            return this;
        }

        /**
         * 黑名单
         */
        public Filter blacks(List<String> values){
            if(values != null){
                this.blacks.addAll(values);
            }
            return this;
        }

        public List<String> getWhites() {
            return whites;
        }

        public List<String> getBlacks() {
            return blacks;
        }
    }


    /**
     * JSON 数据格式验证
     * strArrays格式：
     *  奇数下标：字段名称$验证内容,默认验空 例如： mobile$mobile 验证手机号
     *  偶数下标：报错内容
     */
    public static void validate(JSONObject json, String... strArrays) throws Exception{
        if(strArrays == null || strArrays.length % 2 != 0){
            throw new BusinessException("验证数据格式有误！");
        }
        if(json == null){
            //throw new BusinessException("JSON数据不能为空！");
            return;
        }
        for(int i = 0; i < strArrays.length; i+=2){
            String paramAndCheck = strArrays[i];
            String errorMsg = strArrays[i + 1];
            if(StringTools.isEmpty(errorMsg)){
                errorMsg = "未知错误！";
            }

            String[] array = paramAndCheck.split("\\$");
            String paramName = array[0];    // 字段名称
            String checkName = "empty";    // 验证格式,默认验空
            if(array.length > 1){
                checkName = array[1];
            }
            // 下面的是各种验证
            if("empty".equalsIgnoreCase(checkName)){    // 验空
                if(StringTools.isEmpty(json.getString(paramName))){
                    throw new BusinessException(errorMsg);
                }
            }
            if("email".equalsIgnoreCase(checkName)){    // 验证邮箱格式
                String param = json.getString(paramName);
                if(StringTools.isNotEmpty(param)){
                    OtherTools.regexValidate(param, RegexManage.EMAIL, errorMsg);
                }
            }
        }
    }

    /**
     * 将 JSONObject 数据格式填充到 相关实体类中
     * @param bean
     * @param json
     */
    public static void populate(Object bean, JSONObject json) throws Exception {
        if(bean == null || json == null){
            return;
        }
        Map<String, Object> map = jsonObjectToMap(json);
        BeanTools.populate(bean, json);
    }

    /**
     * 将 JSONObject 数据格式填充到 相关实体类中
     * 扩展：填充数据的过滤
     * @param bean
     * @param json
     * @param filter
     */
    public static void populate(Object bean, JSONObject json, Filter filter) throws Exception {
        JSONTools.filter(json, filter);
        JSONTools.populate(bean, json);
    }

    /**
     * JSONArray 转 List<Map<String, Object>> 格式
     * @param array
     * @return
     */
    public static List<Map<String, Object>> toList(JSONArray array){
        if(array == null || array.isEmpty()){
            return new ArrayList<>();
        }
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for(int i = 0; i < array.size(); i++){
            Map<String, Object> map = jsonObjectToMap(array.getJSONObject(i));
            list.add(map);
        }
        return list;
    }

}
