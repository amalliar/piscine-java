package com.school21.piscinejava.module01.ex05;

public class UserIdsGenerator {
    private static UserIdsGenerator instance;
    private int id;

    private UserIdsGenerator() {
        id = 1;
    }

    public static UserIdsGenerator getInstance() {
        if (instance == null) {
            instance = new UserIdsGenerator();
        }
        return instance;
    }

    public int generateId() {
        return id++;
    }
}
