package banks.chain;

import banks.account.Account;
import banks.tool.Transaction;

public class AbstractHandler implements IWithdrawMoneyHandler{
    private IWithdrawMoneyHandler nextHandler;

    public IWithdrawMoneyHandler setNext(IWithdrawMoneyHandler handler) {
        nextHandler = handler;
        return handler;
    }

    public Transaction handle(Account account, double value) {
        return nextHandler.handle(account, value);
    }
}
