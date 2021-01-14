package dto;

import java.util.Set;

public class AccountDto {

    boolean isSucceed;
    Double balance;
    Set<TransactionDto> transactions;
    String errorMessage;

    public AccountDto(){

    }

    public AccountDto(boolean isSucceed, Double balance, Set<TransactionDto> transactions, String errorMessage) {
        this.isSucceed = isSucceed;
        this.balance = balance;
        this.transactions = transactions;
        this.errorMessage = errorMessage;
    }

    public boolean isSucceed() {
        return isSucceed;
    }

    public void setSucceed(boolean succeed) {
        isSucceed = succeed;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Set<TransactionDto> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<TransactionDto> transactions) {
        this.transactions = transactions;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
