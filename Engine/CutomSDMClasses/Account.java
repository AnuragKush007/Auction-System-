package sdm.engine.CutomSDMClasses;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Account {

    private Set<Transaction> transactions;
    private double balance;

    public Account(){
        this.balance = 0;
        this.transactions = new HashSet<>();
    }

    public void addTransaction(String type, Date date, double amount){
        Transaction newTransaction = new Transaction(type, date, amount, this.balance);
        this.transactions.add(newTransaction);
        this.balance = newTransaction.getBalanceAfter();
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public double getBalance() {
        return balance;
    }
}
