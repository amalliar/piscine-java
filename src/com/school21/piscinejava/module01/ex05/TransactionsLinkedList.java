package com.school21.piscinejava.module01.ex05;

public class TransactionsLinkedList implements TransactionsList {
    private static class Node {
        public Transaction data;
        public Node next;

        public Node(Transaction transaction) {
            data = transaction;
        }
    }

    private Node beg;
    private Node end;
    private int size;

    @Override
    public boolean add(Transaction transaction) {
        if (transaction == null) {
            return false;
        }
        Node newNode = new Node(transaction);
        if (beg == null) {
            beg = newNode;
        } else {
            end.next = newNode;
        }
        end = newNode;
        ++size;
        return true;
    }

    @Override
    public Transaction getById(String id) {
        if (id == null) {
            return null;
        }
        Node cur = beg;
        while (cur != null) {
            if (id.equals(cur.data.getId())) {
                return cur.data;
            }
            cur = cur.next;
        }
        return null;
    }

    @Override
    public boolean removeById(String id) {
        if (id == null) {
            return false;
        }
        Node prev = beg;
        Node cur = beg;
        while (cur != null) {
            if (id.equals(cur.data.getId())) {
                prev.next = cur.next;
                beg = (beg == cur) ? cur.next : beg;
                end = (end == cur) ? prev : end;
                --size;
                return true;
            }
            prev = cur;
            cur = cur.next;
        }
        return false;
    }

    @Override
    public Transaction[] toArray() {
        Transaction[] transactions = new Transaction[size];
        Node cur = beg;
        int i = 0;
        while (cur != null) {
            transactions[i++] = cur.data;
            cur = cur.next;
        }
        return transactions;
    }
}