package com.example.demo.reflect;

import java.lang.reflect.Field;

public class Test {

    @AnnotationTest(value = "潇潇")
    private static int id;

    public static void main(String[] args) throws Exception{
        Class cla = Test.class;
//        Field[] declaredFields = cla.getDeclaredFields();
//        for (Field declaredField : declaredFields) {
//            AnnotationTest fieldAnnotation = declaredField.getDeclaredAnnotation(AnnotationTest.class);
//            if (fieldAnnotation != null) {
//                System.out.println("字段" + declaredField.getName()
//                        + "有注解，注解的value值是：" + fieldAnnotation.value());
//            } else {
//                System.out.println("字段" + declaredField.getName() + "没有注解");
//            }
//        }
        Field field = cla.getDeclaredField("id");
        AnnotationTest fieldAnnotation = field.getDeclaredAnnotation(AnnotationTest.class);
        System.out.println("字段" + field.getName()
                + "有注解，注解的value值是：" + fieldAnnotation.value());
    }
}
