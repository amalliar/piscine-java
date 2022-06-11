package com.school21.piscinejava.module01.ex05;

import java.util.Arrays;

public class UsersArrayList implements UsersList {
    private static final int defaultInitialCapacity = 10;
    private User[] users;
    private int size;

    public UsersArrayList() {
        users = new User[defaultInitialCapacity];
    }

    public UsersArrayList(int initialCapacity) {
        users = new User[initialCapacity];
    }

    @Override
    public boolean add(User user) {
        if (user == null) {
            return false;
        }
        if (size == users.length) {
            users = Arrays.copyOf(users, (int) (users.length * 1.5));
        }
        users[size++] = user;
        return true;
    }

    @Override
    public User get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        return users[index];
    }

    @Override
    public User getById(int id) {
        for (int i = 0; i < size(); ++i) {
            if (users[i].getId() == id) {
                return users[i];
            }
        }
        throw new UserNotFoundException(String.format("User not found for id: %d", id));
    }

    @Override
    public int size() {
        return size;
    }
}
