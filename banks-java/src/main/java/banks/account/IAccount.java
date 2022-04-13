package banks.account;

import banks.tool.Passable;
import banks.tool.Transaction;

public interface IAccount extends Passable {
    public Transaction withdrawMoney(double value);
    public Transaction topUpMoney(double value);
    public Transaction transfer(double value, Account account);
    public void passDay();

}
