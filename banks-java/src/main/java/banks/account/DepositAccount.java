package banks.account;

import banks.entity.Person;
import banks.exception.BankException;
import banks.tool.AccountTypes;
import banks.tool.Transaction;


public class DepositAccount extends Account {
    public DepositAccount(double balance, Person person) {
        super(balance, person);
        typeOfAccount = AccountTypes.DepositAccount;
    }

    @Override
    public Transaction withdrawMoney(double value) {
        if (dayPassed <= dayLimit) {
            throw  new BankException("The term of the deposit account is not over yet");
        }

        if (balance - value <= 0) {
            throw new BankException("Not enough money");
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
        if (dayPassed < dayLimit) {
            accumulation += (yearPercent / 365) * balance;
            if (dayPassed % 30 == 0) {
                balance += accumulation;
                accumulation = 0;
            }
        }

        dayPassed++;
    }

}
