package banks.service;

import banks.account.CreditAccount;
import banks.account.DebitAccount;
import banks.chain.DebitHandler;
import banks.account.DepositAccount;
import banks.builder.PersonBuilder;
import banks.chain.CreditHandler;
import banks.chain.DebitHandler;
import banks.chain.DepositHandler;
import banks.entity.Bank;
import banks.entity.Person;
import banks.exception.BankException;
import banks.tool.Transaction;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CentralBankTest {
    private CentralBank cb;
    private PersonBuilder pb1;
    private PersonBuilder pb2;

    @Before
    public void setUp() {
        cb = new CentralBank();
        pb1 = new PersonBuilder();
        pb2 = new PersonBuilder();
    }

    @Test(expected = BankException.class)
    public void createPerson_PersonWithInvalidPassportID() {
        pb1.setName("Dima", "ASdd");
        pb1.setAddress("Vyazma");
        pb1.setUserSubscribed(true);
        pb1.setPassportId(213);
        Person person = pb1.build();
    }

    @Test(expected = BankException.class)
    public void tryWithdrawMoney_UserHaveNotEnoughMoney() {
        Bank bank = cb.registerBank("Sber1", 3.5, 100, 30, 5000, 100000);
        pb1.setName("Dima", "Prezhentsov");
        pb1.setAddress("dsadsa");
        pb1.setUserSubscribed(true);
        pb1.setPassportId(561954);
        Person person = pb1.build();
        DebitAccount debitAccount = cb.addDebitAccount(bank, 50, person);
        CreditHandler creditHandler = new CreditHandler();
        DepositHandler depositHandler = new DepositHandler();
        DebitHandler debitHandler = new DebitHandler();
        // chain
        creditHandler.setNext(debitHandler).setNext(depositHandler);
        creditHandler.handle(debitAccount, 100);
    }

    @Test(expected = BankException.class)
    public void tryWithdrawMoney_UserHaveReachedCreditLimit() {
        Bank bank = cb.registerBank("Sber2", 3.5, 100, 30, 5000, 1000);
        pb1.setName("Dima", "dsad");
        pb1.setAddress("dsadsa");
        pb1.setUserSubscribed(true);
        pb1.setPassportId(561954);
        Person person = pb1.build();
        CreditAccount creditAccount = cb.addCreditAccount(bank, 0, person);
        cb.withdrawMoneyFromAccount(creditAccount, 6000);
    }

    @Test(expected = BankException.class)
    public void tryWithdrawMoneyFromDepositAccount_TermOfAccountIsNotOver() {
        Bank bank = cb.registerBank("Sber2", 3.5, 100, 30, 5000, 1000);
        pb1.setName("Dima", "dsad");
        pb1.setAddress("dsadsa");
        pb1.setUserSubscribed(true);
        pb1.setPassportId(561954);
        Person person = pb1.build();
        DepositAccount depositAccount = cb.addDepositAccount(bank, 10000, person);
        cb.withdrawMoneyFromAccount(depositAccount, 100);
    }

    @Test(expected = BankException.class)
    public void tryWithdrawMoney_SuspiciousPersonHaveReachedWithdrawLimit() {
        Bank bank = cb.registerBank("Sber3", 3.5, 100, 30, 5000, 500);
        pb1.setName("Dima", "dsad");
        pb1.setAddress("dsadsa");
        pb1.setUserSubscribed(true);
        Person person = pb1.build();
        DebitAccount debitAccount = cb.addDebitAccount(bank, 10000, person);
        cb.withdrawMoneyFromAccount(debitAccount, 100);
    }

    @Test
    public void revokeTransfer_Successfully() {
        Bank bank = cb.registerBank("Sber4", 3.5, 100, 30, 5000, 500);
        pb1.setName("Dima", "dsad");
        pb1.setAddress("dsadsa");
        pb1.setUserSubscribed(true);
        pb1.setPassportId(561954);
        pb2.setName("Dima", "dsad");
        pb2.setAddress("dsadsa");
        pb2.setUserSubscribed(true);
        pb2.setPassportId(561954);
        Person person1 = pb1.build();
        Person person2 = pb2.build();
        DebitAccount deb1 = cb.addDebitAccount(bank, 50000, person1);
        DebitAccount deb2 = cb.addDebitAccount(bank, 70000, person2);
        double deb1BalanceBeforeRevoke = deb1.getBalance();
        double deb2BalanceBeforeRevoke = deb2.getBalance();
        Transaction t = cb.transferToAccount(deb1, deb2, 5000);
        cb.revokeTransfer(t.getTransactionId());
        double deb1BalanceAfterRevoke = deb1.getBalance();
        double deb2BalanceAfterRevoke = deb2.getBalance();
        assertEquals(deb1BalanceBeforeRevoke, deb1BalanceAfterRevoke, 0);
        assertEquals(deb2BalanceBeforeRevoke, deb2BalanceAfterRevoke, 0);
    }

    @Test(expected = BankException.class)
    public void RevokeTransfer_TransferAlreadyRevoked() {
        Bank bank = cb.registerBank("Sber5", 3.5, 100, 30, 5000, 500);
        pb1.setName("Dima", "dsad");
        pb1.setAddress("dsadsa");
        pb1.setUserSubscribed(true);
        pb1.setPassportId(561954);
        pb2.setName("Dima", "dsad");
        pb2.setAddress("dsadsa");
        pb2.setUserSubscribed(true);
        pb2.setPassportId(561954);
        Person person1 = pb1.build();
        Person person2 = pb2.build();
        DebitAccount deb1 = cb.addDebitAccount(bank, 5000, person1);
        DebitAccount deb2 = cb.addDebitAccount(bank, 7000, person2);
        Transaction t = cb.transferToAccount(deb1, deb2, 500);
        cb.revokeTransfer(t.getTransactionId());
        cb.revokeTransfer(t.getTransactionId());
    }
}
