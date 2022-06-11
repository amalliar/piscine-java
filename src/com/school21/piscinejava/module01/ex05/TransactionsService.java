package com.school21.piscinejava.module01.ex05;

import java.util.UUID;

import static com.school21.piscinejava.module01.ex05.Transaction.TransferCategory.CREDIT;
import static com.school21.piscinejava.module01.ex05.Transaction.TransferCategory.DEBIT;

public class TransactionsService {
    private final UsersList users = new UsersArrayList();

    public int addUser(String name, int balance) {
        User user = new User(name, balance);
        users.add(user);
        return user.getId();
    }

    public int getUserBalance(int userId) {
        return users.getById(userId).getBalance();
    }

    public String getUserName(int userId) {
        return users.getById(userId).getName();
    }

    public String makeTransaction(int recipientId, int senderId, int amount) {
        User recipient = users.getById(recipientId);
        User sender = users.getById(senderId);
        if (amount <= 0) {
            throw new IllegalTransactionException("Error: transfer of the non-positive amount");
        }
        if (sender.getBalance() < amount) {
            throw new IllegalTransactionException("Error: transfer of the amount exceeding user's residual balance");
        }
        String transactionId = UUID.randomUUID().toString();
        sender.getTransactionsList().add(new Transaction(transactionId, sender, recipient, CREDIT, amount));
        recipient.getTransactionsList().add(new Transaction(transactionId, sender, recipient, DEBIT, amount));
        sender.setBalance(sender.getBalance() - amount);
        recipient.setBalance(recipient.getBalance() + amount);
        return transactionId;
    }

    public Transaction[] getUserTransactions(int userId) {
        return users.getById(userId).getTransactionsList().toArray();
    }

    public String removeTransaction(int userId, String transactionId) {
        User user = users.getById(userId);
        Transaction toDelete = user.getTransactionsList().getById(transactionId);
        if (toDelete == null) {
            throw new RuntimeException("Error: transaction not found");
        }
        user.getTransactionsList().removeById(transactionId);
        return toDelete.toString();
    }

    public Transaction[] getUnpairedTransactions() {
        TransactionsList unpaired = new TransactionsLinkedList();
        for (int i = 0; i < users.size(); ++i) {
            Transaction[] userTransactions = users.get(i).getTransactionsList().toArray();
            for (Transaction transaction : userTransactions) {
                if (!unpaired.removeById(transaction.getId())) {
                    unpaired.add(transaction);
                }
            }
        }
        return unpaired.toArray();
    }
}
