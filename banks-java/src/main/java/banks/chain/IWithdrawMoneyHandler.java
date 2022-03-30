package banks.chain;

import banks.account.Account;
import banks.tool.Transaction;

public interface IWithdrawMoneyHandler {
    IWithdrawMoneyHandler setNext(IWithdrawMoneyHandler handler);
    Transaction handle(Account account, double value);
}

