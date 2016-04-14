package com.luoo.study.reflect;

import com.luoo.study.json.bo.Book;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by Luoo on 2016/4/5.
 */
public class Example1 {

    public static Object getProperty(Object owner, String fieldName) throws Exception {
        Class ownerClass = owner.getClass();

        Field field = ownerClass.getField(fieldName);

        Object property = field.get(owner);

        return property;
    }

    public static Object getStaticProperty(String className, String fieldName)
            throws Exception {
        Class ownerClass = Class.forName(className);

        Field field = ownerClass.getField(fieldName);

        Object property = field.get(ownerClass);

        return property;
    }

    public static Object invokeMethod(Object owner, String methodName, Object[] args)
            throws Exception {

        Class ownerClass = owner.getClass();

        Class[] argsClass = new Class[args.length];

        for (int i = 0, j = args.length; i < j; i++) {
            argsClass[i] = args[i].getClass();
        }

        Method method = ownerClass.getMethod(methodName, argsClass);

        return method.invoke(owner, args);
    }


    public static void main(String[] args) {
        try {
            Book book = new Book();
           /* int a = 1;
            String b = "99";
            boolean c = true;*/

            Class c = book.getClass();
            Method[] ms = c.getMethods();

            for (int i = 0; i < ms.length; i++) {
                String name = ms[i].getName();

                if (name.startsWith("set")) {
                    Class[] cc = ms[i].getParameterTypes();
                    if(cc.length==1) {
                        String type = cc[0].getName(); // parameter type
                        System.out.println(name + "---------------" + type);
                    }
                }

            }


//            Object o = Example1.invokeMethod(book, "getRemark", new Object[]{a,b,c},new Class[]{});

//            Object o = Example1.getStaticProperty("com.asiainfo.test.luoo.study.json.bo.Book", "remark");

//            System.out.println(o.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
