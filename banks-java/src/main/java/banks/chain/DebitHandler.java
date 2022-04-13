package banks.chain;

import banks.account.Account;
import banks.chain.AbstractHandler;
import banks.tool.AccountTypes;
import banks.tool.Transaction;

public class DebitHandler extends AbstractHandler {

    @Override
    public Transaction handle(Account account, double value) {
        if (account.getTypeOfAccount().equals(AccountTypes.DebitAccount)) {
            return account.withdrawMoney(value);
        }
        return super.handle(account, value);
    }
}