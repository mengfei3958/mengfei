package com.example.demo.reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class UserTest {

    public static void main(String[] args) {
        Class clazz = UserManager.class;
        try {
            Method putValue = clazz.getDeclaredMethod("putValue", Integer.class, User.class);
            Object result = putValue.invoke(clazz.newInstance(), 1, new User("小强"));
            System.out.println(result);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        try {
            Method putValue = clazz.getDeclaredMethod("removeValue", Integer.class);
            Object removeValue = putValue.invoke(clazz.newInstance(), 1);
            System.out.println(removeValue);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }
}
