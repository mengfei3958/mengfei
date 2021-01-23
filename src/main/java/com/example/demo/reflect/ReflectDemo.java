package com.example.demo.reflect;


import lombok.experimental.var;
import org.apache.poi.ss.formula.functions.T;

import java.util.ArrayList;
import java.util.List;

public class ReflectDemo {

//    public <R> List<R> doSomethingAwesome(R thing){
//        List<R> results = new ArrayList<>();
//        for (int i = 0; i < 5; i++)
//        {
//            results.add(R);
//        }
//        return results;
//    }

    public <T extends List> T testGenericity(T genericity)
    {
        genericity.add(1);
        genericity.add(2);
        genericity.add("123");
        System.out.println(genericity);
        return genericity;
    }


}
