package com.school21.piscinejava.module01.ex05;

public interface UsersList {
    boolean add(User user);
    User get(int index);
    User getById(int id);
    int size();
}
