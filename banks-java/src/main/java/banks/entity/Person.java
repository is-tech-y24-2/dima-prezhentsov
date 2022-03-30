package banks.entity;

import banks.account.Account;

import java.util.ArrayList;
import java.util.List;

public class Person {
    private int passportId = 0;
    private String name;
    private String surname;
    private String address;
    private boolean isSubscribed;
    private List<Account> accounts;

    public void setName(String name) {
        this.name = name;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isSubscribed() {
        return isSubscribed;
    }

    public void setSubscribed(boolean subscribed) {
        isSubscribed = subscribed;
    }

    public Person(String name, String surname) {
        this.name = name;
        this.surname = surname;
        accounts = new ArrayList<Account>();
    }

    public int getPassportId() {
        return passportId;
    }

    public void setPassportId(int passportId) {
        this.passportId = passportId;
    }

    public String getName() {
        return name;
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }
}
