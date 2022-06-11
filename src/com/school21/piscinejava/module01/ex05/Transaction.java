package com.school21.piscinejava.module01.ex05;

public class Transaction {
    enum TransferCategory {
        CREDIT("To"),
        DEBIT("From");

        public final String label;

        TransferCategory(String label) {
            this.label = label;
        }
    }

    private final String id;
    private final User recipient;
    private final User sender;
    private final TransferCategory transferCategory;
    private final int transferAmount;

    public Transaction(String id, User sender, User recipient,
                       TransferCategory transferCategory, int transferAmount) {
        this.id = id;
        this.sender = sender;
        this.recipient = recipient;
        this.transferCategory = transferCategory;
        if (transferCategory == TransferCategory.CREDIT && transferAmount > 0
                || transferCategory == TransferCategory.DEBIT && transferAmount < 0) {
            transferAmount = -transferAmount;
        }
        this.transferAmount = transferAmount;
    }

    @Override
    public String toString() {
        return String.format("%s %s %d", transferCategory.label,
                (transferCategory == TransferCategory.CREDIT) ? recipient : sender,
                transferAmount);
    }

    public String getId() {
        return id;
    }

    public User getRecipient() {
        return recipient;
    }

    public User getSender() {
        return sender;
    }

    public TransferCategory getTransferCategory() {
        return transferCategory;
    }

    public int getTransferAmount() {
        return transferAmount;
    }
}
