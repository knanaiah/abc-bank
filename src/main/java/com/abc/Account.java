package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private final int accountType;
    public List<Transaction> transactions;

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
        private double interestEarnedChecking(double amount) {
            return amount * 0.001;
        }

        private double interestEarnedSavings(double amount) {
            if (amount <= 1000)
                return amount * 0.001;
            else
                return 1 + (amount - 1000) * 0.002;
        }

        //Interest is accrued every day of the year, including weekends and holidays.
        private double interestEarnedMaxiSavings(double amount) {
            int dayOfYear = DateProvider.getInstance().DayOfYear();
            if (amount <= 1000)
                return amount * 0.02 * (dayOfYear/365);
            if (amount <= 2000)
                return 20 + ((amount - 1000) * 0.05 * (dayOfYear/365));
            return 70 + ((amount - 2000) * 0.1 * (dayOfYear/365));
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
