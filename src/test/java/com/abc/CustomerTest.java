package com.abc;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

    private static final double DOUBLE_DELTA = 1e-15;

    //Objects belong to class so they can be used by all test methods
    private Account checkingAccount = new Account(Account.CHECKING);
    private Account savingsAccount = new Account(Account.SAVINGS);
    private Account maxiSavingsAccount = new Account(Account.MAXI_SAVINGS);

    @Test //Test customer statement generation
    public void testApp(){
        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);

        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $100.00\n" +
                "Total $100.00\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $4,000.00\n" +
                "  withdrawal $200.00\n" +
                "Total $3,800.00\n" +
                "\n" +
                "Total In All Accounts $3,900.00", henry.getStatement());
    }

    @Test
    public void testOneAccount(){
        Customer oscar = new Customer("Oscar").openAccount(new Account(Account.SAVINGS));
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount(){
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Ignore
    public void testThreeAcounts() {
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        assertEquals(3, oscar.getNumberOfAccounts());
    }

    //Added this test to ensure interest is calculated accurately in Checking.
    @Test
    public void testCheckingInterestEarned() {
        checkingAccount.deposit(100);
        assertEquals(0.08, checkingAccount.interestEarned(), DOUBLE_DELTA);
    }

    //Added this test to ensure interest is calculated accurately in Savings.
    @Test
    public void testSavingsInterestEarned() {
        savingsAccount.deposit(2000);
        assertEquals(2.41, savingsAccount.interestEarned(), DOUBLE_DELTA);
    }

    //Added this test to ensure interest is calculated accurately in Maxi Savings.
    @Test
    public void testMaxiSavingsInterestEarned() {
        //This should ensure a checking balance of $100.00 and savings balance of $3800.00
        maxiSavingsAccount.deposit(5000);
        maxiSavingsAccount.withdraw(1000);
        assertEquals(217.47, maxiSavingsAccount.interestEarned(), DOUBLE_DELTA);
    }

    //Added this test to ensure transactions are calculated accurately.
    @Test
    public void testSumTransactions() {
        //This should ensure a checking balance of $100.00 and savings balance of $3800.00
        checkingAccount.deposit(100.0);
        checkingAccount.deposit(250.0);
        checkingAccount.withdraw(100.0);
        savingsAccount.deposit(3000.0);
        savingsAccount.withdraw(500.0);
        assertEquals(250.00, checkingAccount.sumTransactions(), DOUBLE_DELTA);
        assertEquals(2500.00, savingsAccount.sumTransactions(), DOUBLE_DELTA);
    }
}
