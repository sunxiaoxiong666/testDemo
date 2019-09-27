package reflect;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

public class TestReflect {
    public static void main(String[] args) {
        Dog dog=new Dog();
        setPropertyValue(dog,"name","小花");
        String name = getPropertyValue(dog, "name");
        System.out.println(dog.getName());
        System.out.println(name);

    }

    public static void setPropertyValue(Object obj,String propertyName,String propertyValue){
        PropertyDescriptor pd;
        try {
            pd=new PropertyDescriptor(propertyName,obj.getClass());
            Method writeMethod = pd.getWriteMethod();//获得get方法
            String type = pd.getPropertyType().toString();//获得字段的类型
            Object value = formatItem(propertyValue, type);
            writeMethod.invoke(obj,value);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String getPropertyValue(Object obj,String propertyName){
        PropertyDescriptor pd;
        Object value =null;
        try {
            pd=new PropertyDescriptor(propertyName,obj.getClass());
            Method readMethod = pd.getReadMethod();
             value = readMethod.invoke(obj);
        }catch (Exception e){
            e.printStackTrace();
        }
        return value.toString();
    }

    private static Object formatItem(String value, String type) {
        Object res = null;
        switch (type) {
            case "class java.lang.Integer":
                res = Integer.parseInt(value);
                break;
            case "class java.lang.Long":
                res = Long.parseLong(value);
                break;
            case "class java.lang.Short":
                res = Short.parseShort(value);
                break;
            default:
                res = value;
                break;
        }
        return res;
    }
}
