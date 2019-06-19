package cn.eblcu.questionbank.infrastructure.util;

import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapAndObjectUtils {

    private MapAndObjectUtils(){}
    /**
     * @Author 焦冬冬
     * @Description MAp  转对象
     * @Date 17:05 2019/3/22
     * @Param 
     * @return 
     **/
    public static  Object MapToObject(Map<String,Object> map, Class<?>  entity)throws Exception {
        if (map == null)
            return null;

        Object obj = entity.newInstance();
        BeanUtils.populate(obj,map);

        return obj;
    }
    
    /**
     * @Author 焦冬冬
     * @Description  对象转MAP
     * @Date 17:19 2019/3/22
     * @Param 
     * @return 
     **/
    public static Map<String, Object> ObjectToMap(Object object)throws Exception{
        if(null==object)
            return  null;
        Map<String, Object> map = new HashMap<String, Object>();
        Field[] declaredFields1 = object.getClass().getSuperclass().getDeclaredFields();
        Field[] declaredFields = object.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            map.put(declaredField.getName(),declaredField.get(object));
        }
        return  map;
    }

    /**
     * 携带一级父类的转换
     * @param object
     * @return
     * @throws Exception
     */
    public static Map<String,Object> ObjectToMap2(Object object) throws Exception{
        if(null==object)
            return  null;
        Map<String, Object> map = new HashMap<String, Object>();
        Field[] declaredFields1 = object.getClass().getSuperclass().getDeclaredFields();
        Field[] declaredFields = object.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            if(declaredField.get(object)==null)
                continue;
            map.put(declaredField.getName(),declaredField.get(object));
        }
        for (Field field : declaredFields1) {
            field.setAccessible(true);
            if(field.get(object)==null)
                continue;
            map.put(field.getName(),field.get(object));
        }
        return  map;
    }


    public static <T> T ObjectClone(Object obj, Class<T> toResult) {
        if (obj == null) {
            return null;
        }
        try {
            T t = toResult.newInstance();
            Field[] fields = toResult.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);//修改访问权限
                if (Modifier.isFinal(field.getModifiers()))
                    continue;
                if (isWrapType(field)) {
                    String firstLetter = field.getName().substring(0, 1).toUpperCase(); // 首字母大写
                    String getMethodName = "get" + firstLetter + field.getName().substring(1);
                    String setMethodName = "set" + firstLetter + field.getName().substring(1);
                    //判断源对象中是否有该属性
                    Field[] declaredFields = obj.getClass().getDeclaredFields();
                    boolean isExists=false;
                    for (Field declaredField : declaredFields) {
                        if(declaredField.getName().equals(field.getName())) {
                            isExists=true;
                            break;
                        }
                    }
                    if(!isExists)
                        continue;
                    Method getMethod = obj.getClass().getMethod(getMethodName);   //从源对象获取get方法
                    Method setMethod = toResult.getMethod(setMethodName, new Class[] { field.getType() }); //从目标对象获取set方法
                    Object value = getMethod.invoke(obj); // get 获取的是源对象的值
                    setMethod.invoke(t, new Object[] { value }); // set 设置的是目标对象的值
                }
            }
            return t;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 是否是基本类型、包装类型、String类型
     */
    private static boolean isWrapType(Field field) {
        String[] types = { "java.lang.Integer", "java.lang.Double", "java.lang.Float", "java.lang.Long",
                "java.lang.Short", "java.lang.Byte", "java.lang.Boolean", "java.lang.Char", "java.lang.String", "int",
                "double", "long", "short", "byte", "boolean", "char", "float" };
        List<String> typeList = Arrays.asList(types);
        return typeList.contains(field.getType().getName()) ? true : false;
    }

}
