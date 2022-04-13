package banks.tool;

import banks.account.Account;
import banks.exception.BankException;

import java.sql.PreparedStatement;

public class Transaction {
    private static int id;
    private boolean isTransactionRevoked;
    private Account sender;
    private Account recipient;
    private double value;
    private int transactionId;

    public Transaction(Account sender, Account recipient, double value) {
        this.sender = sender;
        this.recipient = recipient;
        this.value = value;
        transactionId = id++;
        isTransactionRevoked = false;
    }

    public boolean isTransactionRevoked() {
        return isTransactionRevoked;
    }

    public void setTransactionRevoked(boolean transactionRevoked) {
        isTransactionRevoked = transactionRevoked;
    }

    public Account getSender() {
        return sender;
    }

    public Account getRecipient() {
        return recipient;
    }

    public double getValue() {
        return value;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void doTransaction() {
        if (sender.getAccountId() == recipient.getAccountId()) {
            recipient.setBalance(recipient.getBalance() + value);
        }
        sender.setBalance(sender.getBalance() - value);
        recipient.setBalance(recipient.getBalance() + value);
    }

    public void revokeTransaction() {
        if (!isTransactionRevoked) {
            recipient.setBalance(recipient.getBalance() - value);
            sender.setBalance(sender.getBalance() + value);
            isTransactionRevoked = true;
            return;
        }

        throw new BankException("Transaction (id : " + transactionId + ") has already revoked");
    }
}

