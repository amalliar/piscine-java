package com.school21.piscinejava.module01.ex05;

public interface TransactionsList {
    boolean add(Transaction transaction);
    Transaction getById(String id);
    boolean removeById(String id);
    Transaction[] toArray();
}
