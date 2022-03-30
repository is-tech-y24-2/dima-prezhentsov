package banks.service;

import banks.account.Account;
import banks.account.CreditAccount;
import banks.account.DebitAccount;
import banks.account.DepositAccount;
import banks.entity.Bank;
import banks.entity.Person;
import banks.tool.EventListener;
import banks.tool.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CentralBank {
    private List<Bank> banks;
    private List<Transaction> transactions;
    private EventListener skipDay;


    public CentralBank() {
        banks = new ArrayList<>();
        transactions = new ArrayList<>();
        skipDay = new EventListener();
    }

    public Bank registerBank(String name, double yearPercent, double commission, int dayLimit, double creditLimit, double withdrawLimit) {
        Bank tmp = new Bank(name, yearPercent, commission, dayLimit, creditLimit, withdrawLimit);
        banks.add(tmp);
        return tmp;
    }

    public DebitAccount addDebitAccount(Bank bank, double balance, Person person) {
        DebitAccount debitAccount = new DebitAccount(balance, person);
        bank.addAccount(debitAccount);
        bank.addClient(person);
        bank.setAccountCondition(debitAccount);
        person.addAccount(debitAccount);
        skipDay.addDelegate(debitAccount);
        return debitAccount;
    }

    public DepositAccount addDepositAccount(Bank bank, double balance, Person person) {
        DepositAccount depositAccount = new DepositAccount(balance, person);
        bank.addAccount(depositAccount);
        bank.addClient(person);
        bank.setAccountCondition(depositAccount);
        person.addAccount(depositAccount);
        skipDay.addDelegate(depositAccount);
        return depositAccount;
    }

    public CreditAccount addCreditAccount(Bank bank, double balance, Person person) {
        CreditAccount creditAccount = new CreditAccount(balance, person);
        bank.addAccount(creditAccount);
        bank.addClient(person);
        bank.setAccountCondition(creditAccount);
        person.addAccount(creditAccount);
        skipDay.addDelegate(creditAccount);
        return creditAccount;
    }

    public Transaction withdrawMoneyFromAccount(Account account, double value) {
        Transaction tmp = account.withdrawMoney(value);
        transactions.add(tmp);
        return tmp;
    }

    public Transaction topUpAnAccount(Account account, double value) {
        Transaction tmp = account.topUpMoney(value);
        transactions.add(tmp);
        return tmp;
    }

    public Transaction transferToAccount(Account sender, Account recipient, double value) {
        Transaction tmp = sender.transfer(value, recipient);
        transactions.add(tmp);
        return tmp;
    }

    public void revokeTransfer(int suspiciousTransactionId) {
        for (Transaction tmp : transactions.stream().filter(transaction -> transaction.getTransactionId() == suspiciousTransactionId).toList()) {
            tmp.revokeTransaction();
        }
    }

    public void skipDays(int count) {
        for (int i = 0; i < count; i++) {
            skipDay.invoke();
        }
    }
}
