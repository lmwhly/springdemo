package com.luoo.study.reflect;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * Created by Luoo on 2016/4/5.
 */
public class ParameterUtil {

    public static void setFormBean(HttpServletRequest request, Object bean) {
        Class c = bean.getClass();
        Method[] ms = c.getMethods();

        for (int i = 0; i < ms.length; i++) {

            String name = ms[i].getName();

            if (name.startsWith("set")) {
                Class[] cc = ms[i].getParameterTypes();
                if (cc.length == 1) {
                    String type = cc[0].getName();

                    String prop = Character.toLowerCase(name.charAt(3)) + name.substring(4);

                    System.out.println(prop);
                }
            }
        }
    }

    public static void main(String[] args) {


    }
}
