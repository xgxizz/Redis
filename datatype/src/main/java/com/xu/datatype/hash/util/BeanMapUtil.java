package com.xu.datatype.hash.util;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
/**
 * @Description
 * @Author xgx
 * @Date 2019/9/19 14:59
 */
public class BeanMapUtil {
    /**
     * 将Object对象里面的属性和值转化成Map对象
     *
     * @param obj
     * @return
     * @throws IllegalAccessException
     */
    public static Map<String, Object> objectToMap(Object obj) throws IllegalAccessException {
        Map map = new HashMap<String,Object>();
        Class<?> clazz = obj.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            Object value = field.get(obj);
            map.put(fieldName, value);
        }
        return map;
    }
}
