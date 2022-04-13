package banks.chain;

import banks.account.Account;
import banks.tool.AccountTypes;
import banks.tool.Transaction;

public class DepositHandler extends AbstractHandler{
    @Override
    public Transaction handle(Account account, double value) {
        if (account.getTypeOfAccount().equals(AccountTypes.DepositAccount)) {
            return account.withdrawMoney(value);
        }
        return super.handle(account, value);
    }
}
