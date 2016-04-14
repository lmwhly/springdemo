package com.luoo.study.reflect;

import static java.lang.System.out;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GenericParamType {

    // Щ��������ʾ�õ��������еĲ�������������
    public static void main(String args[]) throws Exception {
        /* type ���������͵ĸ߼������ӿڣ���ȻҲ��Class���ĸ���
         * ���ǰ���ԭʼ���͡����������͡��������͡����ͱ����ͻ������͡�

          ������һ�¡�Type ���÷�
          type ��һ�ֱ�ʾ��������е��������͵��೬���ӿڣ������int Integer String
          �ⶼ�Ǳ�ʾһ������Ե����ͣ������е� int.class Integer.class String.class ���Ǳ�ʾ�������͵�ʵ��
          ���������ǰ��ѧϰ�ķ��� Class c = Integer.class Class �൱�ڱ�ʾ���͵���,��Integer.class ����һ��
          ��Ϊ�������͵�����ʵ����
          ������������Щ���䡡��� type �Ͳ����ˣ�type �� class һ�������� type ��һ�ֱȡ�Class ��ʾ��Χ��Ҫ���
          �����ӿڣ�����ʾJava���������͵����нӿ�
        */

        //typeTest();
        //�����ڡ�Type����ʵ���� CenericArrayType �ӿ�
        List list = new ArrayList<Date>();
        //convert();
        typeVariable(list);
        //applyMethod(list);
        //relations();
        //  int[] array = new int[] {1,2,3};
        //genericArrayTypeTest(array);
    }

    public static void applyMethod(List<Date> list) throws Exception {
        Method m = GenericParamType.class.getMethod("applyMethod", List.class);
        Type[] t1 = m.getParameterTypes();// �䷵���ǲ���������
        Type[] t2 = m.getGenericParameterTypes();//�䷵�ص��ǲ����Ĳ�����������,����Ĵ���ʵ�ʵĲ�������
        Method m2 = GenericParamType.class.getMethod("main", String[].class);
        Type[] t3 = m2.getGenericParameterTypes();//��������������ǲ��������͵Ļ�����ô getGenericParameterTypes�ͷ����� getParameterTypes һ����
        Type[] t4 = m2.getParameterTypes();
        out.println(t1[0]);//interface java.util.List
        out.println(t2[0]);//java.util.List<java.util.Date>
        //����ͨ����getGenericParameterTypes �õ����� List<Date>����ô������ô�ܵõ����Ĳ��������͵�ʵ����
        // type ���кö��ӽӿڣ�����ͨ���ӽӿ�������
        out.println(t2.getClass());
        ParameterizedType t = (ParameterizedType) t2[0];//�����������������ת��
        out.println(t.getClass());
        out.println(t.getActualTypeArguments()[0]);// ���Եõ����������͵Ĳ���ʵ��

    }

    public static void convert() {
        List<String> list = new ArrayList<String>();
        Type type = list.getClass().getGenericSuperclass();
        out.println(type);
        ParameterizedType pt = (ParameterizedType) type;
        out.println(pt.getActualTypeArguments()[0]);

        Integer array[] = new Integer[10];
        out.println(array.getClass().isArray());
        Type t1 = array.getClass().getGenericSuperclass();
        out.println(t1);
        //  GenericArrayType pt2 = (GenericArrayType)t1;
    }

    public static void relations() {
        Type types = Class.class.getGenericSuperclass();
        out.println(types);
    }


    public static void typeTest() {
        Type t1 = Integer.class;
        out.println(t1);
        Type t2 = String.class;
        out.println(t2);
        Type t3 = int[].class;
        out.println(t3);
        Type t4 = int[][].class;
        out.println(t4);
        Type t5 = Class.class;
        out.println(t5);
        Class t6 = Class.class;
        out.print(t6);
    }

    public static void typeVariable(List<Date> list) {
        out.println(list.getClass());
        TypeVariable tval[] = list.getClass().getTypeParameters();
        TypeVariable v = tval[0];
        Type t[] = v.getBounds();
        out.println(t.length);
        out.println(v.getGenericDeclaration().getTypeParameters()[0]);

        /*out.println(tval.length);
        for(TypeVariable val:tval)
            out.println(val);*/
    }


}


