package banks.account;

import banks.entity.Person;
import banks.exception.BankException;
import banks.tool.AccountTypes;
import banks.tool.Transaction;

public class CreditAccount extends Account{
    public CreditAccount(double balance, Person person) {
        super(balance, person);
        typeOfAccount = AccountTypes.CreditAccount;
    }

    @Override
    public Transaction withdrawMoney(double value) {
        if ((balance - value) * -1 > creditLimit) {
            throw new BankException("User has reached the credit limit");
        }

        return super.withdrawMoney(value);
    }

    @Override
    public Transaction transfer(double value, Account account) {
        if ((balance - value) * -1 > creditLimit) {
            throw new BankException("User has reached the credit limit");
        }

        return super.transfer(value, account);
    }

    @Override
    public void passDay() {
        if (balance < constant.PERMISSIBLE_VALUE) {
            accumulation += commission;
        }

        if (dayPassed % constant.MONTH_DAYS == 0) {
            balance -= accumulation;
            accumulation = 0;
        }
        dayPassed++;
    }
}
