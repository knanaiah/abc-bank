package com.abc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private final int accountType;
    public List<Transaction> transactions;

    private int dayOfYear = DateProvider.getInstance().DayOfYear();

    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
        }
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(-amount));
        }
    }

    //Replaced SWITCH statement with calls to Inner Class Methods
    public double interestEarned() {
        double amount = sumTransactions();
        Account.EarnedInterest earnedInterest = new Account.EarnedInterest();
        if (accountType == CHECKING) return earnedInterest.interestEarnedChecking(amount);
        else if (accountType == SAVINGS) return earnedInterest.interestEarnedSavings(amount);
        else return earnedInterest.interestEarnedMaxiSavings(amount);
    }

    //Encapsulated account specific interest calculation within a nested class.
    //Can also replace this with child classes for each account type - Polymorphism design
    class EarnedInterest {
        BigDecimal bd;

        //Interest is accrued every day of the year, including weekends and holidays.
        private double interestEarnedChecking(double amount) {
            bd = new BigDecimal((amount * 0.001 * dayOfYear) / 365);
            return bd.setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
        }

        //Interest is accrued every day of the year, including weekends and holidays.
        private double interestEarnedSavings(double amount) {
            if (amount <= 1000) {
                bd = new BigDecimal((amount * 0.001 * dayOfYear) / 365);
                return bd.setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
            }
            else {
                bd = new BigDecimal(((1000 * 0.001 * dayOfYear) / 365) + ((amount - 1000) * 0.002 * dayOfYear) / 365);
                return bd.setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
            }
        }

        //Interest is accrued every day of the year, including weekends and holidays.
        private double interestEarnedMaxiSavings(double amount) {
            double interest;
            if (amount <= 1000) {
                bd = new BigDecimal((amount * 0.02 * dayOfYear) / 365);
                interest = bd.setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
            }
            else if (amount <= 2000) {
                bd = new BigDecimal((1000 * 0.02 * dayOfYear) / 365 + ((amount - 1000) * 0.05 * dayOfYear) / 365);
                interest = bd.setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
            }
            else {
                bd = new BigDecimal((1000 * 0.02 * dayOfYear) / 365 + (1000 * 0.05 * dayOfYear) / 365 + ((amount - 2000) * 0.1 * dayOfYear) / 365);
                interest = bd.setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
            }
            return interest;
        }
   }

    //Removed the boolean. Unnecessary if it is always true.
    public double sumTransactions() {
       return checkIfTransactionsExist();
    }

    //Removed boolean argument since all transactions are being checked.
    //Need to overload this method if specific transactions are to be checked
    //or modify code within the method.
    private double checkIfTransactionsExist() {
        double amount = 0.0;
        for (Transaction t : transactions)
            amount += t.amount;
        return amount;
    }

    public int getAccountType() {
        return accountType;
    }

}
