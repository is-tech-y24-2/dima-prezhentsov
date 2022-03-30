package banks.account;

import banks.entity.Person;
import banks.exception.BankException;
import banks.tool.AccountTypes;
import banks.tool.Transaction;

import java.util.ArrayList;
import java.util.List;


public class DebitAccount extends Account {
    public DebitAccount(double balance, Person person) {
        super(balance, person);
        typeOfAccount = AccountTypes.DebitAccount;
    }

    @Override
    public Transaction withdrawMoney(double value) {
        if (balance - value <= 0) {
            throw new BankException("Not enough money");
        }

        if (isSuspicionPerson || value >= withdrawLimit) {
            throw new BankException("User " + person.getName() + " have withdraw limit = " + withdrawLimit);
        }

        return super.withdrawMoney(value);
    }

    @Override
    public Transaction transfer(double value, Account account) {
        if (balance - value < 0) {
            throw new BankException("not enough money");
        }

        return super.transfer(value, account);
    }

    @Override
    public void passDay() {
        accumulation += (yearPercent / 365) * balance;
        if (dayPassed % 30 == 0) {
            balance += accumulation;
            accumulation = 0;
        }

        dayPassed++;
    }

}
