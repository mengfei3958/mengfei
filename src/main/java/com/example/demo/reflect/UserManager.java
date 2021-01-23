package com.example.demo.reflect;

import java.util.HashMap;
import java.util.Map;

public class UserManager {

    private Map<Integer, User> userMap = new HashMap<>();

    public Map<Integer, User> putValue(Integer number, User user) {
        userMap.put(number, user);
        return userMap;
    }

    public Map<Integer, User> removeValue(Integer number) {
        userMap.remove(number);
        return userMap;
    }

}
