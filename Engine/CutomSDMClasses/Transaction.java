package sdm.engine.CutomSDMClasses;

import java.util.Date;

public class Transaction {

    public enum TransactionType {
        LOAD("load"),
        CREDIT("credit"),
        DEBIT("debit")
        ;

        private String transactionTypeStr;

        TransactionType(String transactionType) {
            this.transactionTypeStr = transactionType;
        }

        public String getTransactionTypeStr() {
            return  transactionTypeStr;
        }
    }

    private TransactionType type;
    private Date date;
    private double amount;
    private double balanceBefore;
    private double balanceAfter;

    public Transaction(String type, Date date, double amount, double balanceBefore) {
        this.type = convertStringToTransactionType(type);
        this.date = date;
        this.amount = amount;
        this.balanceBefore = balanceBefore;
        if(this.type.equals(TransactionType.DEBIT))
            this.balanceAfter = balanceBefore - amount;
        else
            this.balanceAfter = balanceBefore + amount;
    }

    private static TransactionType convertStringToTransactionType(String transactionType) {
        if (transactionType.toLowerCase().contains("load")) {
            return TransactionType.LOAD;
        }
        else if (transactionType.toLowerCase().contains("credit")) {
            return TransactionType.CREDIT;
        }
        return TransactionType.DEBIT;
    }

    public TransactionType getType() {
        return type;
    }

    public Date getDate() {
        return date;
    }

    public double getAmount() {
        return amount;
    }

    public double getBalanceBefore() {
        return balanceBefore;
    }

    public double getBalanceAfter() {
        return balanceAfter;
    }
}
