package banks.account;

import banks.entity.Person;
import banks.tool.AccountTypes;
import banks.tool.Transaction;

public abstract class Account implements IAccount {
    private static int id;
    protected double withdrawLimit;
    protected int accountId;
    protected double balance;
    protected double yearPercent;
    protected double commission;
    protected int dayLimit;
    protected double accumulation;
    protected int dayPassed;
    protected double creditLimit;
    protected Person person;
    protected boolean isSuspicionPerson;
    protected AccountTypes typeOfAccount;

    public Account(double balance, Person person) {
        this.balance = balance;
        this.person = person;
        accountId = id++;
        dayLimit = 0;
        yearPercent = 0;
        commission = 0;
        creditLimit = 0;
        dayPassed = 0;
        accumulation = 0;
        withdrawLimit = 0;
        isSuspicionPerson = person.getPassportId() == 0;
        typeOfAccount = AccountTypes.Default;
    }

    public double getWithdrawLimit() {
        return withdrawLimit;
    }

    public void setWithdrawLimit(double withdrawLimit) {
        this.withdrawLimit = withdrawLimit;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getYearPercent() {
        return yearPercent;
    }

    public void setYearPercent(double yearPercent) {
        this.yearPercent = yearPercent;
    }

    public double getCommission() {
        return commission;
    }

    public void setCommission(double commission) {
        this.commission = commission;
    }

    public int getDayLimit() {
        return dayLimit;
    }

    public void setDayLimit(int dayLimit) {
        this.dayLimit = dayLimit;
    }

    public double getAccumulation() {
        return accumulation;
    }

    public void setAccumulation(double accumulation) {
        this.accumulation = accumulation;
    }

    public int getDayPassed() {
        return dayPassed;
    }

    public void setDayPassed(int dayPassed) {
        this.dayPassed = dayPassed;
    }

    public double getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(double creditLimit) {
        this.creditLimit = creditLimit;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public boolean isSuspicionPerson() {
        return isSuspicionPerson;
    }

    public void setSuspicionPerson(boolean suspicionPerson) {
        isSuspicionPerson = suspicionPerson;
    }

    public AccountTypes getTypeOfAccount() {
        return typeOfAccount;
    }

    public void setTypeOfAccount(AccountTypes typeOfAccount) {
        this.typeOfAccount = typeOfAccount;
    }

    public Transaction withdrawMoney(double value) {
        Transaction transaction = new Transaction(this, this, -value);
        transaction.doTransaction();
        return transaction;
    }

    public Transaction topUpMoney(double value) {
        Transaction transaction = new Transaction(this, this, value);
        transaction.doTransaction();
        return transaction;
    }

    public Transaction transfer(double value, Account account) {
        Transaction transaction = new Transaction(this, account, value);
        transaction.doTransaction();
        return transaction;
    }

}
