package com.school21.piscinejava.module01.ex05;

public class User {
    private final int id = UserIdsGenerator.getInstance().generateId();
    private final TransactionsList transactionsList = new TransactionsLinkedList();
    private String name;
    private int balance;

    public User(String name, int balance) {
        this.name = name;
        this.balance = Math.max(balance, 0);
    }

    public int getId() {
        return id;
    }

    public TransactionsList getTransactionsList() {
        return transactionsList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return String.format("%s(id = %d)", name, id);
    }
}
