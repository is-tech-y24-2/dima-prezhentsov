package banks.entity;

import banks.account.Account;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Bank {
    private static int id;
    private int bankID;
    private double yearPercent;
    private double commission;
    private int dayLimit;
    private double creditLimit;
    private double withdrawLimit;
    private String name;
    private List<Account> accounts;
    private List<Person> people;

    public List<Person> getPeople() {
        return people;
    }

    public Bank(
            String name,
            double yearPercent,
            double commission,
            int dayLimit,
            double creditLimit,
            double withdrawLimit
    ) {
        this.bankID = ++id;
        this.name = name;
        this.people = new ArrayList<>();
        this.accounts = new ArrayList<>();
        updateConditions(yearPercent, commission, dayLimit, creditLimit, withdrawLimit);
    }

    public String getName() {
        return name;
    }

    public int getBankID() {
        return bankID;
    }

    public void updateConditions(
            double yearPercent,
            double commission,
            int dayLimit,
            double creditLimit,
            double withdrawLimit
    ) {
        this.yearPercent = yearPercent / 100;
        this.commission = commission;
        this.dayLimit = dayLimit;
        this.creditLimit = creditLimit;
        this.withdrawLimit = withdrawLimit;
        for (Account account : accounts) {
            setAccountCondition(account);
        }
        for (Person person : people.stream().filter(person -> person.isSubscribed()).toList()) {
            System.out.println("Bank's (" + name + ") conditions have update:\n" +
                    "Year Percent: " + yearPercent + "\n" +
                    "Commission: " + commission + "\n" +
                    "Day Limit: " + dayLimit + "\n" +
                    "Credit Limit: " + creditLimit + "\n" +
                    "Withdraw Limit: " + withdrawLimit);
        }
    }

    public void setAccountCondition(Account account) {
        account.setYearPercent(yearPercent);
        account.setCommission(commission);
        account.setDayLimit(dayLimit);
        account.setCreditLimit(creditLimit);
        account.setWithdrawLimit(withdrawLimit);
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    public void addClient(Person client) {
        people.add(client);
    }
}
