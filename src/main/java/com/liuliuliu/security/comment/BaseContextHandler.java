package com.liuliuliu.security.comment;

import com.liuliuliu.security.constant.GlobalConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author lty
 * @Date 2019/11/28 14:46
 */
public class BaseContextHandler {

    public static ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<Map<String, Object>>();

    public static void set(String key, Object value) {
        Map<String, Object> map = threadLocal.get();
        if (map == null) {
            map = new HashMap<String, Object>();
            threadLocal.set(map);
        }
        map.put(key, value);
    }

    public static Object get(String key){
        Map<String, Object> map = threadLocal.get();
        if (map == null) {
            map = new HashMap<String, Object>();
            threadLocal.set(map);
        }
        return map.get(key);
    }

    public static Map<String, Object> getMap() {
        return threadLocal.get();
    }

    public static void setMap(Map<String, Object> map) {
        threadLocal.set(map);
    }

    public static String getUserID(){
        Object value = get(GlobalConstant.CONTEXT_USER_ID);
        return returnObjectValue(value);
    }

    public static String getUsername(){
        Object value = get(GlobalConstant.CONTEXT_USER_NAME);
        return returnObjectValue(value);
    }

    public static String getDeptId(){
        Object value = get(GlobalConstant.CONTEXT_DEPT_ID);
        return returnObjectValue(value);
    }

    public static String getSystemType(){
        Object value = get(GlobalConstant.CONTEXT_SYSTEM_TYPE);
        return returnObjectValue(value);
    }

    public static String getUserToken(){

        Object value = get(GlobalConstant.CONTEXT_USER_TOKEN);

        return returnObjectValue(value);

    }

    public static String getApiToken(){

        Object value = get(GlobalConstant.CONTEXT_API_TOKEN);

        return returnObjectValue(value);

    }

    public static void setUserID(String userID){
        set(GlobalConstant.CONTEXT_USER_ID,userID);
    }

    public static void setUsername(String username){
        set(GlobalConstant.CONTEXT_USER_NAME,username);
    }

    public static void setDeptId(String deptId){
        set(GlobalConstant.CONTEXT_DEPT_ID,deptId);
    }

    public static void setSystemType(String systemType){
        set(GlobalConstant.CONTEXT_SYSTEM_TYPE,systemType);
    }

    public static void setUserToken(String userToken){

        set(GlobalConstant.CONTEXT_USER_TOKEN,userToken);
    }

    public static void setApiToken(String apiToken){

        set(GlobalConstant.CONTEXT_API_TOKEN,apiToken);
    }


    private static String returnObjectValue(Object value) {
        return value==null?null:value.toString();
    }

    public static void remove(){
        threadLocal.remove();
    }
}
