package com.example.demo.reflect;

import java.lang.reflect.Field;

public class ReflectTest {

    private String userName = "小明";

    public static void changePropertyName(ReflectTest obj, String propertyName, Object value) throws Exception {
        Class cls = obj.getClass();
        Field field = cls.getDeclaredField(propertyName);
        field.setAccessible(true);
        field.set(obj, value);
    }

    public static void main(String[] args) throws Exception {
        ReflectTest d = new ReflectTest();
        changePropertyName(d, "userName", "小花");
        System.out.println(d.userName);

    }
}
